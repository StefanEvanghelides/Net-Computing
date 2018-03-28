import datetime
import json

import pika as pika

received_complaint = dict()
received_complaint["type"] = "Noice"
received_complaint["description"] = "This is an uninspired description"
received_complaint["location"] = "Nijenborg 1"
received_complaint["name"] = "Stefan Evanghejsadc"
received_complaint["sender_ip"] = "127.0.0.1"

connection = pika.BlockingConnection(pika.ConnectionParameters(host='172.20.10.8'))
channel = connection.channel()

channel.queue_declare(queue='incomming')

js = json.dumps(received_complaint)

channel.basic_publish(exchange='',
                      routing_key='incomming',
                      body=js)

connection.close()
