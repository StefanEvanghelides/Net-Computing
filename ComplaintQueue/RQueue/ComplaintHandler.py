import json

from datetime import datetime


class ComplaintHandler():
    def __init__(self, distributor, database):
        self.distributor = distributor
        self.database = database

        self.distributor.consume(self.handle_complaint)

    def handle_complaint(self, ch, method, properties, body):
        print(body)
        try:
            _json = json.loads(body)
            received_complaint = dict()
            # Basic info
            received_complaint["type"] = _json["type"]
            received_complaint["description"] = _json["description"]
            received_complaint["location"] = _json["location"]
            received_complaint["resolved"] = False
            received_complaint["timestamp"] = datetime.now()

            # Sender info
            sender_info = dict()
            sender_info["name"] = _json["name"]
            sender_info["sender-ip"] = _json["sender_ip"]
            received_complaint["sender"] = sender_info
        except Exception as e:
            print("An exception occured")
            return
        self.database.insert(received_complaint)
        print("Stored something in the database")