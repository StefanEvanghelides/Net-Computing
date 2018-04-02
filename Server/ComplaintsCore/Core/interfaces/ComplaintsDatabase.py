from abc import abstractmethod

class ComplaintsDatabase(object):
    """
    By adhering to this Interface we make sure that the system
    still works if we decide to use a different database system.
    """
    @abstractmethod
    def insert(self, complaint):
        """Insert the given complaint in the database"""
        pass

    @abstractmethod
    def get(self, _id):
        """Get the complaint with the given if from the database"""
        pass

    @abstractmethod
    def update(self, _id, dict):
        """Update the complaint with the given id with the data in the dict"""
        pass

    @abstractmethod
    def get_all(self, querry):
        """Get all complaints from the database that fit the given query"""
        pass

