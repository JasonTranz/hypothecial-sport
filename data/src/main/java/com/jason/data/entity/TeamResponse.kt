package com.jason.data.entity

import com.google.gson.annotations.SerializedName

data class TeamResponse(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("logo") val logo: String? = null
)