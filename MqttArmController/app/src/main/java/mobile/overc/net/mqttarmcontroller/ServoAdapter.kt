package mobile.overc.net.mqttarmcontroller

/**
 * Represents an adapter for an individual PWM Controller for a Servo from a SeekBar.
 * The duty cycle of the PWM controller for the servo controls the angle of rotation: the angle of rotation is a
 * linear function of the duty cycle
 * <p/>
 * [min] and [max] represent the range of the arm, in terms of duty cycle.
 * These physical constraints on the servo- e.g. The servo must not be given a duty cyle of less than 7.5,
 * because there is something in the way, at that angle of rotation.
 *
 * Created by turloughcowman on 29/12/2017.
 */

data class ServoAdapter(val min: Float, val max: Float){

    /**
     * Convert the input values to a duty cycle that can be used by the servo.
     * The division [value]/[limit] represents the a projection to a value between [min] and [max],
     * that will be the angle of rotation of the servo.
     * <p/>
     * A [value] of 0 maps to [min], and if [value] == [limit], then it maps to [max].
     * Between these values the mapping is linear
     */
    fun toDutyCycle(value: Int, limit: Int): Float {
        val diff = (max - min)/ limit
        return min + value * diff
    }
}
