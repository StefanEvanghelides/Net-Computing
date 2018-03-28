from flask_restplus import Resource

from API.endpoints.Complaint import db


class SenderInfo(Resource):
    def get(self,complaint_id):
        complaint = db.get(complaint_id)
        return complaint["sender"]
