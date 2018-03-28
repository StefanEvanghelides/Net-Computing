from bson import ObjectId
from pymongo import MongoClient, DESCENDING

from interfaces.ComplaintsDatabase import ComplaintsDatabase


class MongoComplaintsDatabase(ComplaintsDatabase):
    def __init__(self):
        db_client = MongoClient('localhost', 27017)
        self.db = db_client["ComplaintQueue"]
        self.collection = self.db["Complaints"]

    def insert(self, complaint):
        self.collection.insert_one(complaint)

    def get(self, _id):
        return self.collection.find_one({"_id" : ObjectId(_id)})

    def update(self, _id, dict):
        self.collection.update( {'_id': ObjectId(_id)}, {'$set': dict})

    def get_all(self, querry):
        return list(self.collection.find(querry).sort('timestamp', DESCENDING))

