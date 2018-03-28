from Core.MongoComplaintsDatabase import MongoComplaintsDatabase
from flask import request
from flask_restplus import Resource

from API.endpoints.Complaint import db


class Complaint(Resource):
    def get(self,complaint_id):
        complaint = db.get(complaint_id)
        item = {
            "_id": str(complaint['_id']),
            "type": complaint['type'],
            "description": complaint['description'],
            "location": complaint['location'],
            "resolved": complaint['resolved'],
            "timestamp": complaint['timestamp'].strftime("%Y-%m-%d %H:%M:%S"),
            "sender-info" : complaint['sender']
        }
        return item
