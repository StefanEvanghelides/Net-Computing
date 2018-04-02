from Core.MongoComplaintsDatabase import MongoComplaintsDatabase
from DatabaseComplaintHandler import DatabaseComplaintHandler
from Core.RabbitComplaintsQueue import RabbitComplaintsQueue

db = MongoComplaintsDatabase("localhost", 27017, "ComplaintsQueue", "Complaints")
queue = RabbitComplaintsQueue("localhost", exchange="Complaints")
handler = DatabaseComplaintHandler(db)
handler.connect_queue(queue)

if __name__ == '__main__':
    queue.connect()
