package com.jason.sport.util

import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun String.encodeUrl(): String {
    return URLEncoder.encode(this, StandardCharsets.UTF_8.toString())
}

fun String.decodeUrl(): String {
    return URLDecoder.decode(this, StandardCharsets.UTF_8.toString())
}