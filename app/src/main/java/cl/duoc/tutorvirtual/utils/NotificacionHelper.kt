package cl.duoc.tutorvirtual.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

class NotificacionHelper(private val context: Context) {

    private val CANAL_ID = "tutor_virtual_canal"
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        crearCanalNotificacion()
    }

    private fun crearCanalNotificacion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(
                CANAL_ID,
                "Recordatorios de Hitos",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Canal para notificar fechas límite de entregas"
            }
            notificationManager.createNotificationChannel(canal)
        }
    }

    fun mostrarNotificacion(titulo: String, mensaje: String, notificacionId: Int = 1) {
        val builder = NotificationCompat.Builder(context, CANAL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(titulo)
            .setContentText(mensaje)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        notificationManager.notify(notificacionId, builder.build())
    }
}