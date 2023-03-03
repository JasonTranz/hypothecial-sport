package com.jason.data.mapper

import com.jason.data.entity.MatchResponse
import com.jason.domain.entity.Match

fun MatchResponse.toMatch(): Match {
    return Match(
        date = this.date ?: "",
        description = this.description ?: "",
        home = this.home ?: "",
        away = this.away ?: "",
        winner = this.winner ?: "",
        highlights = this.highlights ?: ""
    )
}