package com.projet.mobile.growth.rendu.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.projet.mobile.growth.rendu.model.Training
import java.util.*

object NotificationScheduler {
    fun scheduleNotifications(context: Context, weeklyPlan: Map<Int, List<Training>>) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        weeklyPlan.forEach { (dayOfWeek, trainings) ->
            if (trainings.isNotEmpty()) {
                val targetCalendarDay = when(dayOfWeek) {
                    0 -> Calendar.MONDAY
                    1 -> Calendar.TUESDAY
                    2 -> Calendar.WEDNESDAY
                    3 -> Calendar.THURSDAY
                    4 -> Calendar.FRIDAY
                    5 -> Calendar.SATURDAY
                    6 -> Calendar.SUNDAY
                    else -> Calendar.MONDAY
                }

                val calendar = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, 17)
                    set(Calendar.MINUTE, 8)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }

                val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)

                var daysUntilNotification = (targetCalendarDay - 1) - today

                if (daysUntilNotification < 0) {
                    daysUntilNotification += 7
                }

                if (daysUntilNotification == 0 && calendar.before(Calendar.getInstance())) {
                    daysUntilNotification = 7
                }

                calendar.add(Calendar.DAY_OF_YEAR, daysUntilNotification)

                trainings.forEach { training ->
                    val intent = Intent(context, NotificationReceiver::class.java).apply {
                        putExtra("title", "Séance prévue demain")
                        putExtra("message", training.title)
                    }

                    val requestCode = (dayOfWeek * 1000) + training.id.hashCode()

                    val pendingIntent = PendingIntent.getBroadcast(
                        context,
                        requestCode,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
                        alarmManager.setAndAllowWhileIdle(
                            AlarmManager.RTC_WAKEUP,
                            calendar.timeInMillis,
                            pendingIntent
                        )
                    } else {
                        alarmManager.setExactAndAllowWhileIdle(
                            AlarmManager.RTC_WAKEUP,
                            calendar.timeInMillis,
                            pendingIntent
                        )
                    }
                }
            }
        }
    }
}
