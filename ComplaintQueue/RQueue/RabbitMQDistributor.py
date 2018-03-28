import pika


class RabbitMQDistributor:
    def __init__(self):
        self.connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
        self.channel = self.connection.channel()
        self.channel.queue_declare(queue='incomming')

    def start(self):
        print("Starting rabbit mq")
        self.channel.start_consuming()
        self.consume(self.call)

    def consume(self, callback):
        self.channel.basic_consume(callback,
                                   queue="incomming",
                                   no_ack=True)
