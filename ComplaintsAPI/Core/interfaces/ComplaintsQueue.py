from abc import abstractmethod

class ComplaintsQueue(object):
    """Interface for a Queue containing complaints. By adhering to this interface we
    can enforce that the system should still use when we switch to a different Queue"""
    @abstractmethod
    def connect(self):
        """Connect to the Queue"""
        pass

    @abstractmethod
    def consume(self, callback):
        """Method called by handlers to indicate that they wish to handle complaints.
        The given callback function is used to signal message to the handler."""
        pass
