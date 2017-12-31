# Robot-Arm
Python PWM controller (for Raspberry pi) and Android app (kotlin) for controlling it via MQTT. 
The Android app sends control signals to the Pi, which are used to signal its GPIO pins. These, in turn, control the servo motors.

PythonController runs on the PI (requires mqtt: http://www.switchdoc.com/2016/02/tutorial-installing-and-testing-mosquitto-mqtt-on-raspberry-pi/). In this sample, the server is the public one at test.mosquitto.org. Replace this with your own server.
File mqtt_arm_controller.py receives and parses the message text for the arm. It delegates GPIO control to single_motor.py.

MqttArmController is an Android app. Open this with Intellij or Android Studio to build and deploy it. It is responsible for 'converting' SeekBar position to the correct PWM duty cycle for the motor, and for sending the instructions via MQTT.

