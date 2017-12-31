#Single servo controller
import RPi.GPIO as GPIO
GPIO.setmode(GPIO.BCM)

dictionary = {}

pin = 4
GPIO.setup(pin,GPIO.OUT)
pwm  = GPIO.PWM(pin,50)
pwm.start(7.0)

#Lazy initialization of PWM for the pin, if it has not already been configured
def setPin(value):
	if(value not in dictionary):
		GPIO.setup(value,GPIO.OUT)
		pwm  = GPIO.PWM(value,50)
		pwm.start(7.0)
		dictionary[value] = pwm
		print "Created pin ", value
	pin = value
	pwm = dictionary[pin]

	print "Using pin ", pin

#With the current pin, change the dutyCycle to rotate the servo
def setDutyCycle(dutyCycle):
	print "Setting duty cycle to {0} for pin {1}".format( dutyCycle, pin)
	pwm.ChangeDutyCycle(dutyCycle)

#Change pin, then move
def move(pin, dutyCycle):
	setPin(pin)
	setDutyCycle(dutyCycle)

#Cleanup
def cleanup():
	print "Cleaning up"
	GPIO.cleanup()