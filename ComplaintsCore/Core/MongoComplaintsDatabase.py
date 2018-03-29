from bson import ObjectId
from pymongo import MongoClient, DESCENDING

from interfaces.ComplaintsDatabase import ComplaintsDatabase


class MongoComplaintsDatabase(ComplaintsDatabase):
    """
    Implementation of the ComplaintsDatatbase for a MongoDB based complaints database.
    """
    def __init__(self, ip, port, db_name, collection_name):
        db_client = MongoClient(ip, port)
        self.db = db_client[db_name]
        self.collection = self.db[collection_name]

    def insert(self, complaint):
        self.collection.insert_one(complaint)

    def get(self, _id):
        return self.collection.find_one({"_id" : ObjectId(_id)})

    def update(self, _id, dict):
        self.collection.update( {'_id': ObjectId(_id)}, {'$set': dict})

    def get_all(self, querry):
        return list(self.collection.find(querry).sort('timestamp', DESCENDING))

