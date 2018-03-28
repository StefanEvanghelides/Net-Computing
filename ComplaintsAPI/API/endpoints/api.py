from flask_restplus import Api

from API.endpoints.Complaint.complaint import Complaint
from API.endpoints.Complaint.complaints import Complaints
from API.endpoints.Complaint.resolved import Resolved
from API.endpoints.Complaint.sender_info import SenderInfo

API = Api(
    version='0.1'
    , title='ComplaintsAPI'
    , description='A public API to retrieve registered complaints'
)

API.add_resource(Complaints, '/complaints')
API.add_resource(Complaint, '/complaints/<string:complaint_id>')
API.add_resource(SenderInfo, '/complaints/<string:complaint_id>/sender-info')
API.add_resource(Resolved, '/complaints/<string:complaint_id>/resolved')