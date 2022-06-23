package com.fernandez.aplicacionsensores

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_counter_step.*


class CounterStepActivity: AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null
    var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter_step)

        val actionBar = supportActionBar
        actionBar?.hide()



        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {

                Log.v("FIREBASE_MESSASING","Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            Log.v("FIREBASE_MESSASING","New Token: $token")

        })










        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (sensor != null) {
            Log.v("VERIFICANDO_SENSOR", "Sensor existe")
        } else {
            Log.v("VERIFICANDO_SENSOR", "Sensor no existe")
        }


        floatingStart.setOnClickListener {
            counter = 0
            textContador.text = counter.toString()
            floatingStart.visibility = View.GONE
            floatingEnd.visibility = View.VISIBLE
            sensor?.also {
                sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_FASTEST)
            }
        }
        //obtenerTodosLosSensores()
    }

    // Informar cuando se tiene un valor nuevo
    override fun onSensorChanged(sensorEvent: SensorEvent) {
        val paso = sensorEvent.values[0]
        counter += paso.toInt()
        textContador.text = counter.toString()
    }

    // La exactitud de cambio de un sensor
    override fun onAccuracyChanged(sensor: Sensor?, p1: Int) {

    }


}