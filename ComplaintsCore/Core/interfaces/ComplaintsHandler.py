from abc import abstractmethod


class ComplaintsHandler(object):
    def connect_queue(self, queue):
        queue.consume(self.handle_complaint)

    @abstractmethod
    def handle_complaint(self, ch, method, properties, body):
        pass