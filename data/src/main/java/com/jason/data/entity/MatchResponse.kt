package com.jason.data.entity

import com.google.gson.annotations.SerializedName

data class MatchResponse(
    @SerializedName("date") val date: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("home") val home: String? = null,
    @SerializedName("away") val away: String? = null,
    @SerializedName("winner") val winner: String? = null,
    @SerializedName("highlights") val highlights: String? = null,
)
