import pika

from interfaces.ComplaintsQueue import ComplaintsQueue

class RabbitComplaintsQueue(ComplaintsQueue):
    def __init__(self, ip):
        self.connection = pika.BlockingConnection(pika.ConnectionParameters(host=ip))
        self.channel = self.connection.channel()
        self.channel.exchange_declare(exchange='complaints',
                                      exchange_type='fanout')
        result = self.channel.queue_declare(exclusive=True)
        self.queue = result.method.queue
        self.channel.queue_bind(exchange='complaints',
                                 queue=self.queue)

    def connect(self):
        print("Starting rabbit mq connection")
        self.channel.start_consuming()
        self.consume(self.call)

    def consume(self, callback):
        self.channel.basic_consume(callback,
                                   queue=self.queue,
                                   no_ack=True)
