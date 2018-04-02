# Decentralised server structure
The functionality of our server is distributed over 3 different project. Two of these projects depend on a running instance of RabbitMQ and two depend on a MongoDB database being available. Currently it is hardcoded that these machines should be available on the same machine as the running projects. It is however possible to set a different IP for these dependencies in the code, allowing the platform to be spread over 5 different machines.

The three projects are divided over 3 different folders.
- ComplaintQueue : Receives complaints from RabbitMQ and stores them in the MongoDB database
- ComplaitsEmailer : Receives complaints from RabbitMQ and sends an email to registered receivers
- ComplaintsAPI : Provides the client application with a RESTful api to retrieve complaints from the database

All three projects depend on the classes defined in the ComplaintsCore folder. We will explain in the setup section below how one can run all components depending on the Core module.

## Setup
First, make sure RabbitMQ and MongoDB are running locally. Alternatively, run RabbitMQ and MongoDB on separate machines and change the IP and port in the Core module, specifically the MongoComplaintsDatabase and the RabbitComplaintsQueue.

To run all three python projects it is required to install a list of dependencies. It is recommended to use a clean virtualenvironment for this. To simplify the setup we added a requirements.txt file that can be used in combination with the command `pip install < requirements.txt` to install all dependencies.

As stated earlier all projects depend on the Core module. For this reason we need to make sure that all projects have access to this module before we run them. It is recommended to add the Core module to your python source path. However, one can also decide to simply copy the Core folder directly into the project folders. Make sure to only copy the Core folder and not the containing ComplaintsCore folder and to paste the Core directly to the root folder of the project.

Now that the projects have access to the Core module we can run them. Before running the commands below, make sure you cd into the correct project folder.
- ComplaintsEmail : `python ComplaintsEmailer.py`
- ComplaintsAPI : `python ComplaintsAPI.py`
- ComplaintQueue : `python IncomingComplaintsQueue.py`
