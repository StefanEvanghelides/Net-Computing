from MongoComplaintsDatabase import MongoComplaintsDatabase
from RQueue.DatabaseComplaintHandler import DatabaseComplaintHandler
from RabbitComplaintsQueue import RabbitComplaintsQueue

db = MongoComplaintsDatabase()
cd = RabbitComplaintsQueue("localhost")
h = DatabaseComplaintHandler(cd, db)

if __name__ == '__main__':
    cd.connect()