from abc import abstractmethod


class ComplaintsHandler(object):
    """
    This abstract class provide an interface to create a Handler that handles
    incoming messages from a ComplaintsQueue
    """
    def connect_queue(self, queue):
        """Connect to the given ComplaintsQueue and start consuming messages"""
        queue.consume(self.handle_complaint)

    @abstractmethod
    def handle_complaint(self, ch, method, properties, body):
        """Callback function that is called by the ComplaintsQueue with a message"""
        pass