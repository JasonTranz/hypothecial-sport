package com.jason.domain.entity

data class Match(
    val date: String = "",
    val description: String = "",
    var home: Team = Team(),
    var away: Team = Team(),
    val winner: String = "",
    val highlights: String = "",
    val isFavorite: Boolean = false,
    val isReminder: Boolean = false
)

data class MatchCollectionList(
    val previousMatches: List<Match> = emptyList(),
    val upcomingMatches: List<Match> = emptyList()
)

enum class MatchState(val value: String) {
    PREVIOUS("previous"),
    UPCOMING("upcoming")
}
