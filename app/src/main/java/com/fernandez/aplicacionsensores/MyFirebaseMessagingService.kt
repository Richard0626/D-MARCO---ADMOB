package com.fernandez.aplicacionsensores

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {



    // Recibimimos el contenido de la notificacion
    override fun onMessageReceived(message: RemoteMessage) {
        createNotificationChannel()
        Log.v("FIREBASE_MESSASING","From: ${message.from}")
        Log.v("FIREBASE_MESSASING","From: ${message.data}")
        Log.v("FIREBASE_MESSASING","From: ${message.notification}")
        val titulo = message.data["titulo"]
        val mensaje = message.data["mensaje"]
        showNotify(titulo,mensaje)
    }

    // Generar el token del dispositivo
    override fun onNewToken(token: String) {
        Log.v("FIREBASE_MESSASING","New Token: $token")
    }

    fun showNotify(titulo: String?, contenido:String?){

        val notification = NotificationCompat.Builder(this,getString(R.string.channel_id)).also {
            it.setContentTitle(titulo)
            it.setContentText(contenido)
            it.setSmallIcon(R.drawable.ic_baseline_directions_run_24)
            it.setStyle(NotificationCompat.BigTextStyle().bigText(contenido))
            it.color = Color.RED
            it.priority = NotificationCompat.PRIORITY_HIGH
                }.build()

        val notificationManager: NotificationManagerCompat = NotificationManagerCompat.from(this)
        notificationManager.notify(1, notification)

    }

    private fun createNotificationChannel() {
        val name = getString(R.string.channel_name)
        val descriptionText = getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(getString(R.string.channel_id), name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}