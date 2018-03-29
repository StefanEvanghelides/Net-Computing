from EmailComplaintHandler import EmailComplaintHandler
from RabbitComplaintsQueue import RabbitComplaintsQueue

config = eval(open('config', 'r').read())

queue = RabbitComplaintsQueue("localhost", exchange="Complaints")
email = EmailComplaintHandler(config)
email.connect_queue(queue)


if __name__ == '__main__':
    queue.connect()