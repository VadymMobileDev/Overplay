package com.example.overplaytest.viewmodel

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.overplaytest.util.Quaternion
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.launch
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class MainViewModel : ViewModel(), SensorEventListener {

    private lateinit var sensor: Sensor

    private var timestamp: Float = 0f

    private val orientationQuaternion: Quaternion = Quaternion()

    private val deltaQuaternion: Quaternion = Quaternion()

    private var gyroscopeRotationVelocity = 0.0

    private val quaternion: Quaternion = Quaternion()

    val textSizeLiveData = MutableLiveData<Float>()

    var timerDropDown: Boolean = false

    private fun updatedTextSize(value: Float) {
        textSizeLiveData.postValue(value)
    }

    val timerLiveData = MutableLiveData<Boolean>()

    private fun timerValue(value: Boolean) {
        timerLiveData.postValue(value)
    }

    override fun onSensorChanged(event: SensorEvent?) {

        if (event!!.sensor.type == Sensor.TYPE_GYROSCOPE) {

            if (timestamp != 0f) {
                gyroscopeSensorChangedCalculate(event)
            }

            timestamp = event.timestamp.toFloat() ?: 0f
            var degrees = (2.0f * acos(quaternion.w.toDouble()) * 180.0f / Math.PI).toFloat()
            degreesCalculation(degrees)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    private fun gyroscopeSensorChangedCalculate(event: SensorEvent) {

        val dT: Float = (event.timestamp - timestamp) * NS2S

        var axisX = event.values[0]
        var axisY = event.values[1]
        var axisZ = event.values[2]

        gyroscopeRotationVelocity = sqrt((axisX * axisX + axisY * axisY + axisZ * axisZ).toDouble())

        if (gyroscopeRotationVelocity > EPSILON) {
            axisX /= gyroscopeRotationVelocity.toFloat()
            axisY /= gyroscopeRotationVelocity.toFloat()
            axisZ /= gyroscopeRotationVelocity.toFloat()
        }
        val thetaOverTwo: Double = gyroscopeRotationVelocity * dT / 2.0f
        val sinThetaOverTwo = sin(thetaOverTwo)
        val cosThetaOverTwo = cos(thetaOverTwo)
        deltaQuaternion.x = (sinThetaOverTwo * axisX).toFloat()
        deltaQuaternion.y = (sinThetaOverTwo * axisY).toFloat()
        deltaQuaternion.z = (sinThetaOverTwo * axisZ).toFloat()
        deltaQuaternion.w = (-cosThetaOverTwo).toFloat()


        deltaQuaternion.multiplyByQuat(orientationQuaternion, orientationQuaternion)
        quaternion.set(orientationQuaternion)
        quaternion.w(-quaternion.w())
    }

    private fun degreesCalculation(degrees: Float) {

        val deltaRotationMatrix = FloatArray(16) { 0f }
        SensorManager.getRotationMatrixFromVector(deltaRotationMatrix, quaternion.array())

        when {
            quaternion.z < -0.2 && degrees > 29 && degrees < 179 -> {
                updatedTextSize(20f)
            }
            quaternion.z > 0.2 && degrees > 29 && degrees < 179 -> {
                updatedTextSize(12f)
            }
            degrees < 0.2 || degrees > 359.0 -> {
                updatedTextSize(16f)
            }
        }
    }

    fun sensorCreate(sensorManager: SensorManager) {
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        }
    }

    fun sensorResume(sensorManager: SensorManager) {
        sensorManager.registerListener(this, this.sensor, SensorManager.SENSOR_DELAY_GAME)
    }

    fun sensorStop(sensorManager: SensorManager) {
        sensorManager.unregisterListener(this, this.sensor)
    }


/*   for this implementation, you can use both Services,
     Work Manager or RxJava, since in the task there was a
     recommendation to use the coroutines and flow I settled on this option
*/
    fun tickerTimerHandler() {
        viewModelScope.launch {

            val ticker = ticker(1000, 0)
            var count = 0

            for (event in ticker) {
                count++
                if (timerDropDown && count < TIMER_MAX_VALUE) {
                    timerValue(true)
                    break
                }

                if (count == TIMER_MAX_VALUE) break
            }
            ticker.cancel()
        }
    }

    companion object {
        private const val NS2S = 1.0f / 1000000000.0f
        private const val EPSILON = 0.1
        private const val TIMER_MAX_VALUE = 600000
    }
}