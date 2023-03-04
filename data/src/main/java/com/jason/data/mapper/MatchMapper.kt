package com.jason.data.mapper

import com.jason.data.entity.MatchCollectionListResponse
import com.jason.data.entity.MatchResponse
import com.jason.domain.entity.MatchCollectionList
import com.jason.domain.entity.Match
import com.jason.domain.entity.Team

fun MatchResponse.toMatch(): Match {
    return Match(
        date = this.date ?: "",
        description = this.description ?: "",
        home = Team(name = this.home ?: ""),
        away = Team(name = this.away ?: ""),
        winner = this.winner ?: "",
        highlights = this.highlights ?: ""
    )
}

fun MatchCollectionListResponse.toMatchCollectionList(): MatchCollectionList {
    return MatchCollectionList(
        previousMatches = this.matches?.previous?.map { it.toMatch() } ?: emptyList(),
        upcomingMatches = this.matches?.upcoming?.map { it.toMatch() } ?: emptyList()
    )
}