from RQueue.ComplaintHandler import ComplaintHandler
from RQueue.MongoComplaintsDatabase import MongoComplaintsDatabase
from RQueue.RabbitMQDistributor import RabbitMQDistributor

db = MongoComplaintsDatabase()
cd = RabbitMQDistributor()
h = ComplaintHandler(cd, db)

if __name__ == '__main__':
    cd.start()