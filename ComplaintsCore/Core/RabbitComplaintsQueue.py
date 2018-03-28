import pika

from interfaces.ComplaintsQueue import ComplaintsQueue

class RabbitComplaintsQueue(ComplaintsQueue):
    def __init__(self, ip):
        self.connection = pika.BlockingConnection(pika.ConnectionParameters(host=ip))
        self.channel = self.connection.channel()
        self.channel.queue_declare(queue='incomming')

    def connect(self):
        print("Starting rabbit mq connection")
        self.channel.start_consuming()
        self.consume(self.call)

    def consume(self, callback):
        self.channel.basic_consume(callback,
                                   queue="incomming",
                                   no_ack=True)
