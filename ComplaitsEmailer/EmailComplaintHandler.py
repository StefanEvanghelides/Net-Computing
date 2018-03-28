import json

from datetime import datetime

from email_sending import send_email_about_complaints
from interfaces.ComplaintsHandler import ComplaintsHandler


class EmailComplaintHandler(ComplaintsHandler):
    def __init__(self, config):
        super()
        self.config = config
        self.street_count = dict()
        for item in self.config["receivers"]:
            streetname = item["street"].lower()
            info =  {
                        "email" : item["email"],
                        "count" : 0,
                        "max_count" : item["count"],
                        "complaints" : []
                     }
            self.street_count[streetname] = info

    def count_street(self, street, description, type):
        info = self.street_count[street]
        comp = {
            "type" : type,
            "description" : description
        }
        info["complaints"].append(comp)
        if info["count"] + 1 > info["max_count"]:
            self.send_email(self.config["smtp"], info)
            info["count"] = 0
            info["complaints"] = []
        else:
            info["count"] = info["count"] + 1
        self.street_count[street] = info


    def handle_complaint(self, ch, method, properties, body):
        print(body)
        try:
            _json = json.loads(body)
            location_string = _json["location"].lower()
            type = _json["type"]
            description = _json["description"]
        except Exception as e:
            print("An exception happened")
            return

        for street in self.street_count.keys():
            if street in location_string:
                self.count_street(street, description, type)

    def send_email(self, smtp, info):
        server = smtp["server"]
        from_email = smtp["email"]
        password = smtp["password"]
        to_email = info["email"]
        complaints = info["complaints"]

        send_email_about_complaints(server, from_email, password, to_email, complaints)




