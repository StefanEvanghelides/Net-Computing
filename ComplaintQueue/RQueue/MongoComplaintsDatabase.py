from bson import ObjectId
from pymongo import MongoClient


class MongoComplaintsDatabase:
    def __init__(self):
        db_client = MongoClient('localhost', 27017)
        self.db = db_client["ComplaintQueue"]
        self.collection = self.db["Complaints"]

    def insert(self, complaint):
        self.collection.insert_one(complaint)
