package mobile.overc.net.mqttarmcontroller

import android.util.Log
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

/**
 * Represents a prefabricated Mqtt publisher.
 * Created by turloughcowman on 29/12/2017.
 */

class Mqtt {

    private val TAG = "Mqtt"

    private val topic = "turlough/robot/arm"
    private val qos = 2
    private val broker = "tcp://test.mosquitto.org:1883"
    private val clientId = "Android App"
    private val persistence = MemoryPersistence()
    private val client = MqttClient(broker, clientId, persistence)
    private val connOpts = MqttConnectOptions()

    @Synchronized fun open(){
        connOpts.isCleanSession = true
        Log.v(TAG, "Connecting to broker: " + broker)
        client.connect(connOpts)
        Log.i(TAG, "Connected")
    }

    @Synchronized fun close(){
        client.disconnect()
        Log.w(TAG, "Disconnected")
    }

    @Synchronized fun send(content: String) {

        try {
            Log.v(TAG, "Publishing message: " + content)
            val message = MqttMessage(content.toByteArray())
            message.qos = qos
            client.publish(topic, message)
            Log.i(TAG, "Message published")

        } catch (me: MqttException) {
            Log.e(TAG, "msg " + me.message)
        }

    }
}
