from abc import abstractmethod

class ComplaintsQueue(object):

    @abstractmethod
    def connect(self):
        pass

    @abstractmethod
    def consume(self, callback):
        pass
