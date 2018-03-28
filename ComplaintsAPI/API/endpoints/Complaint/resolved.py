import parser

from flask import request, abort
from flask_restplus import Resource

from API.Database.MongoComplaintsDatabase import MongoComplaintsDatabase

db = MongoComplaintsDatabase()

class Resolved(Resource):
    def get(self,complaint_id):
        complaint = db.get(complaint_id)
        return complaint["resolved"]

    def put(self,complaint_id):
        try:
            resolved_string = request.data.decode("utf-8")
            if resolved_string in ["False", "false"]:
                complaint = db.update(complaint_id, {"resolved": False})
            elif resolved_string in ["True", "true"]:
                complaint = db.update(complaint_id, {"resolved": True})
            else:
                abort(400, 'Value could not be parsed')
        except Exception as e:
            abort(500, 'Exception thrown in setting the resolved value')
        return


