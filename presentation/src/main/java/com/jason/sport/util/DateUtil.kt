package com.jason.sport.util

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