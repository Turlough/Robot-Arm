#import paho.mqtt.client as mqtt
import paho.mqtt.client as mqtt
import RPi.GPIO as GPIO
import json
from collections import namedtuple

base=4
topic = "turlough/robot/arm"
elbow=22
claw=27

address = "test.mosquitto.org"
port = 1883
topic = "turlough/robot/arm"
#GPIO.setmode(GPIO.BCM)
#GPIO.setup(base,GPIO.OUT)
#GPIO.setup(shoulder,GPIO.OUT)
#GPIO.setup(elbow,GPIO.OUT)
#GPIO.setup(claw,GPIO.OUT)

#b = GPIO.PWM(base,50)
#s = GPIO.PWM(shoulder,50)
#e = GPIO.PWM(elbow,50)
#c = GPIO.PWM(claw,50)

#b.start(7.5)
#s.start(7.5)
#e.start(7.5)
#c.start(7.5)

def _json_object_hook(d): return namedtuple('X', d.keys())(*d.values())
def json2obj(data): return json.loads(data, object_hook=_json_object_hook)

import single_motor as servo
def send_command(command):
	print("_________________")
	print("Command Received:\t {0}".format(command))
	print("_________________") 
	print "{0}: {1}".format(command.pin, command.dutyCycle)

	servo.move(command.pin, command.dutyCycle)
	
# The callback for whtopic)

def on_connect(client, userdata, flags, rc):
    print("Connected to '{0}:{1}' with result code '{2}'".format(address, port, rc))

    # Subscribing in on_connect() means that if we lose the connection and
    # reconnect then subscriptions will be renewed.
    client.subscribe(topic)

# The callback for when a PUBLISH message is received from the server.
def on_message(client, userdata, msg):
	data = str(msg.payload)
	#print(data)
	#receive and parse the json
	command = json2obj(data)
	send_command(command)

client = mqtt.Client("RobotClient", protocol=mqtt.MQTTv31)
client.on_connect = on_connect
client.on_message = on_message

client.connect(address, port, 60)

# Blocking call that processes network traffic, dispatches callbacks and
# handles reconnecting.
# Other loop*() functions are available that give a threaded interface and a
# manual interface.

try:
	client.loop_forever()
except KeyboardInterrupt:
	servo.cleanup()
