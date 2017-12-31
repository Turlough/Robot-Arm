package mobile.overc.net.mqttarmcontroller

import com.google.gson.GsonBuilder

/**
 * Created by turloughcowman on 29/12/2017.
 */
class ServoMessage(val pin: Int, var dutyCycle: Float) {

    fun toJson(): String = GsonBuilder()
            .setPrettyPrinting()
            .create()
            .toJson(this)
}