from flask_restplus import Resource

from API.Database.MongoComplaintsDatabase import MongoComplaintsDatabase

db = MongoComplaintsDatabase()

class SenderInfo(Resource):
    def get(self,complaint_id):
        complaint = db.get(complaint_id)
        return complaint["sender"]
