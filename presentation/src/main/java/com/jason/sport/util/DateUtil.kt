package com.jason.sport.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun String.toTimestamp(
    pattern: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
): Long {
    return try {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        val date = sdf.parse(this)
        date.time
    } catch (ex: Exception) {
        0L
    }
}

fun String.changeDateFormat(
    toPattern: String,
    fromPattern: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
    locale: Locale? = null
): String {
    try {
        var lc = Locale.getDefault()
        locale?.let {
            lc = locale
        }
        val df: DateFormat =
            SimpleDateFormat(fromPattern, lc)
        df.timeZone = TimeZone.getTimeZone("UTC")
        val date = df.parse(this)
        val df2: DateFormat =
            SimpleDateFormat(toPattern, Locale.getDefault())
        df.timeZone = TimeZone.getDefault()
        date?.let {
            return df2.format(date)
        }
    } catch (_: Exception) {
        return ""
    }
    return ""
}