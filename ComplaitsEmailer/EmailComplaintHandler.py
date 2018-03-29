import json

from smtplib import SMTP
from interfaces.ComplaintsHandler import ComplaintsHandler


class EmailComplaintHandler(ComplaintsHandler):
    """
    This implementation of the ComplaintsHandler receives incoming complaints. If a certain amount
    of message for a specific region is received a email is send to a registered region receiver.
    """
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
        """
        Counts the times a complaint about a street was received and sends an email
        if needed.
        :param street: Name of the street that received the complaint
        :param description: Description of the complaints
        :param type: Type of the complaint
        :return:
        """
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
        """
        Send an email
        :param smtp: Information about the smtp server that can be used for sending messages
        :param info: Information about the complaints received in an area
        """
        server = smtp["server"]
        from_email = smtp["email"]
        password = smtp["password"]
        to_email = info["email"]
        complaints = info["complaints"]

        from email.mime.text import MIMEText

        body = "\r\n Hi there,\n\nNew complaints are available: \n\n"

        for complaint in complaints:
            description = complaint["description"]
            type = complaint["type"]
            body = body + "[" + str(type) + "] -- " + str(description) + "\n"

        body = body + "\n\nBest,\nThe Complaining Team\r\n"

        message = MIMEText(body)
        message['From'] = from_email
        message['To'] = to_email
        message['Subject'] = 'New complaints available'

        server = SMTP(server)
        server.ehlo()
        server.starttls()
        server.login(user=from_email, password=password)
        server.sendmail(from_addr=from_email, to_addrs=to_email, msg=str(message))
        server.quit()




