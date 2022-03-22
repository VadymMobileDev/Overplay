package com.example.overplaytest.ui

import android.content.Context
import android.content.SharedPreferences
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.overplaytest.R
import com.example.overplaytest.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    private lateinit var sensorManager: SensorManager

    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sharedPref = this?.getPreferences(Context.MODE_PRIVATE) ?: return

        viewModel.sensorCreate(sensorManager)

        fragmentTextUpdateObserver()
        sessionCount()

        clear_button.setOnClickListener {
            sharedPrefEdit(0)
            text_session_count.text = getString(R.string.session_count, sharedPrefRead())

        }
    }

    private fun fragmentTextUpdateObserver() {
        text_session_count.text = getString(R.string.session_count, sharedPrefRead())

        viewModel.textSizeLiveData.observe(this) { v ->
            text_session_count.textSize = v
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.timerDropDown = true
        viewModel.sensorResume(sensorManager)
    }

    override fun onStop() {
        super.onStop()
        viewModel.sensorStop(sensorManager)
        viewModel.timerDropDown = false
        viewModel.tickerTimerHandler()
    }

    private fun sessionCount() {
        viewModel.timerLiveData.observe(this) { v ->
            sharedPrefEdit(sharedPrefRead() + 1)
            text_session_count.text = getString(R.string.session_count, sharedPrefRead())
        }
    }

    private fun sharedPrefEdit(value: Int) {
        with(sharedPref.edit()) {
            putInt(SESSION_COUNT, value)
            apply()
        }
    }

    private fun sharedPrefRead(): Int {
        return sharedPref.getInt(SESSION_COUNT, 0)
    }

    companion object {
        private val SESSION_COUNT = "session_count"
    }
}