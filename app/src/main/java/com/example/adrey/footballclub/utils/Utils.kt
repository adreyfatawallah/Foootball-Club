package com.example.adrey.footballclub.utils

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import android.view.View
import org.jetbrains.anko.collections.forEachWithIndex
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun addReminder(context: Context, title: String, date: String, time: String) {
        val formatDate = SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale("id", "ID"))
        val dtstart = formatDate.parse("$date $time")
        val dtend = Date(dtstart.time + (90 * 60000))
//        val startMillis = Calendar.getInstance().run {
//            set(2012, 0, 19, 7, 30)
//            timeInMillis
//        }
//        val endMillis: Long = Calendar.getInstance().run {
//            set(2012, 0, 19, 8, 30)
//            timeInMillis
//        }
        val intent = Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, dtstart.time)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, dtend.time)
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.DESCRIPTION, "$title $date $time")
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "Watching on TV!!")
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
//                .putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com")
        context.startActivity(intent)
    }

    fun split(string: String?): String = if (string != null) {
        var result = ""
        val split = string.split(";")
        split.forEachWithIndex { i, s ->
            result += s
            if (i < split.size - 2)
                result += "\n"
        }
        result
    } else ""

    fun formatDate(string: String): String = try {
        val inputDate = SimpleDateFormat("dd/MM/yy", Locale("id", "ID"))
        val outputDate = SimpleDateFormat("EE, dd MMM yyyy", Locale("id", "ID"))
        val date = inputDate.parse(string)
        outputDate.format(date)
    } catch (e: ParseException) {
        ""
    }

    fun formatTime(string: String): String = try {
        val inputDate = SimpleDateFormat("HH:mm:ss", Locale("id", "ID"))
        val outputDate = SimpleDateFormat("HH:mm a", Locale("id", "ID"))
        val date = inputDate.parse(string)
        outputDate.format(date)
    } catch (e: ParseException) {
        ""
    }

    fun View.visible() {
        visibility = View.VISIBLE
    }

    fun View.gone() {
        visibility = View.GONE
    }

//    val contentResolver = context.contentResolver
//    val values = ContentValues()
//    values.put(CalendarContract.Events.TITLE, "Testing")
//    values.put(CalendarContract.Events.DESCRIPTION, "Hahaha")
//    values.put(CalendarContract.Events.EVENT_LOCATION, "Somewhere")
//    values.put(CalendarContract.Events.DTSTART, Calendar.getInstance().timeInMillis)
//    values.put(CalendarContract.Events.DTEND, Calendar.getInstance().timeInMillis+60+60*1000)
//    values.put(CalendarContract.Events.CALENDAR_ID, 1)
//    values.put(CalendarContract.Events.EVENT_TIMEZONE, Calendar.getInstance().timeZone.id)
//    val uri = contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)
//
//    val eventID = uri.lastPathSegment.toLong()
//    val values2 = ContentValues().apply {
//        put(CalendarContract.Reminders.MINUTES, 5)
//        put(CalendarContract.Reminders.EVENT_ID, eventID)
//        put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT)
//    }
//    val uri2 = contentResolver.insert(CalendarContract.Reminders.CONTENT_URI, values2)
}