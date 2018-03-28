from abc import abstractmethod

class ComplaintsDatabase(object):

    @abstractmethod
    def insert(self, complaint):
        pass

    @abstractmethod
    def get(self, _id):
        pass

    @abstractmethod
    def update(self, _id, dict):
        pass

    @abstractmethod
    def get_all(self, querry):
        pass

