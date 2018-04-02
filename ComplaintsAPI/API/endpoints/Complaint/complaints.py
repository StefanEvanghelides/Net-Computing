from flask import json, request
from flask_restplus import Resource

from API.endpoints.Complaint import db


class Complaints(Resource):
    def get(self):
        """
        Get a list of complaints from the database
        :return: list of complaints
        """
        count_string = request.args.get('count')
        if count_string is None:
            count = 10
        else:
            count = int(count_string)
        type = request.args.get('type')
        resolved = request.args.get("resolved")
        complaints = db.get_all(self.create_querry(type, resolved))
        return_list = list()
        for comp in complaints[:count]:
            item = {
                "_id": str(comp['_id']),
                "type": comp['type'],
                "description": comp['description'],
                "location": comp['location'],
                "resolved": comp['resolved'],
                "timestamp": comp['timestamp'].strftime("%Y-%m-%d %H:%M:%S"),
                "sender-info": comp['sender']
            }
            return_list.append(item)
        return return_list

    def create_querry(self, type, resolved):
        querry = dict()
        if type is not None:
            querry["type"] = type
        if resolved is not None:
            querry["resolved"] = (resolved in ["True", "true"])

        return querry