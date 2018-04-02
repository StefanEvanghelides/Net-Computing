from flask_restplus import Resource

from endpoints.Complaint import db

class Complaint(Resource):
    def get(self,complaint_id):
        """
        Get the complaint from the database
        :param complaint_id: Id of the to get complaint
        :return:
        """
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
