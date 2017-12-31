package mobile.overc.net.mqttarmcontroller

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private val mqtt = Mqtt()
    private val servoMessage = ServoMessage(4, 4.5f)
    private val servo = ServoAdapter(8.0f, 12.0f)

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                //TODO
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                //TODO
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                //TODO
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        mqtt.open()

        seekBar.setOnSeekBarChangeListener( object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val scaled = servo.toDutyCycle(progress, seekBar.max)
                servoMessage.dutyCycle = scaled
                thread{mqtt.send(servoMessage.toJson())}
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?){}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
}
