from flask_restplus import Resource

from API.endpoints.Complaint import db


class SenderInfo(Resource):
    def get(self,complaint_id):
        """
        Get information about the sender of a specific complaint from the database
        :param complaint_id: Id of the complaint
        :return: dictionaryh with information
        """
        complaint = db.get(complaint_id)
        return complaint["sender"]
