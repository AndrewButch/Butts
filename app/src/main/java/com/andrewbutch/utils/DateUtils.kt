package com.andrewbutch.utils

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    // date sample "Mar 16, 2013 7:24:21 AM"
    val simpleDateFormat: SimpleDateFormat


    init {
        simpleDateFormat = SimpleDateFormat("MMM dd, yyyy K:mm:ss a", Locale.ENGLISH)
    }

    fun convertStringDateToLong(date: String): Long {
        try {
            val parsedDate = simpleDateFormat.parse(date)
            return parsedDate?.time ?: 0L
        } catch (e: ParseException) {
            Log.d("DateUtils", "convertStringDateToLong() with argument: $date parse error: $e")
        }
        return 0L
    }

    // Convert to format "Mar 16, 2013 7:24:21 AM"
    fun convertLongDateToString(date: Long): String {
        return simpleDateFormat.format(Date(date))
    }


}