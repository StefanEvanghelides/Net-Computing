from flask import request, abort
from flask_restplus import Resource

from API.endpoints.Complaint import db


class Resolved(Resource):
    def get(self,complaint_id):
        """
        Get the resolved state from a complaint from the database
        :param complaint_id: Id of the complaint from which we caller want so not the resolved state
        :return: boolean resolve state
        """
        complaint = db.get(complaint_id)
        return complaint["resolved"]

    def put(self,complaint_id):
        """
        Update the resolved state from a complaint in the database
        :param complaint_id: Id of the complaint
        """
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


