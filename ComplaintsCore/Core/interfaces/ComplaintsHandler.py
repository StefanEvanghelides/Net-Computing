from abc import abstractmethod


class ComplaintHandler(object):

    @abstractmethod
    def handle_complaint(self, complaint):
        pass