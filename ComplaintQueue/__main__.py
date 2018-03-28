from MongoComplaintsDatabase import MongoComplaintsDatabase
from RQueue.DatabaseComplaintHandler import DatabaseComplaintHandler
from RabbitComplaintsQueue import RabbitComplaintsQueue

db = MongoComplaintsDatabase()
cd = RabbitComplaintsQueue("localhost")
h = DatabaseComplaintHandler(db)
h.connect_queue(cd)

if __name__ == '__main__':
    cd.connect()