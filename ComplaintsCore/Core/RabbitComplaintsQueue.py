import pika

from Core.interfaces.ComplaintsQueue import ComplaintsQueue

class RabbitComplaintsQueue(ComplaintsQueue):
    """
    RabbitMQ based implementation for the ComplaintsQueue
    """
    def __init__(self, ip, exchange, port=None):
        if port is None:
            self.connection = pika.BlockingConnection(pika.ConnectionParameters(host=ip))
        else:
            self.connection = pika.BlockingConnection(pika.ConnectionParameters(host=ip, port=port))
        self.channel = self.connection.channel()
        self.channel.exchange_declare(exchange=exchange,
                                      exchange_type='fanout')
        result = self.channel.queue_declare(exclusive=True)
        self.queue = result.method.queue
        self.channel.queue_bind(exchange=exchange,
                                 queue=self.queue)

    def connect(self):
        print("Starting rabbit mq connection")
        self.channel.start_consuming()

    def consume(self, callback):
        self.channel.basic_consume(callback,
                                   queue=self.queue,
                                   no_ack=True)
