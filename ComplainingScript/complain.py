import datetime
import json

import pika as pika

received_complaint = dict()
received_complaint["type"] = "Noise"
received_complaint["description"] = "This is an uninspired description"
received_complaint["location"] = "Wassenberghstraat 1"
received_complaint["name"] = "Stefan Evanghejsadc"
received_complaint["sender_ip"] = "127.0.0.1"

connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
channel = connection.channel()

channel.exchange_declare(exchange='complaints',
                         exchange_type='fanout')

js = json.dumps(received_complaint)

channel.basic_publish(exchange='complaints',
                      routing_key='',
                      body=js)

connection.close()