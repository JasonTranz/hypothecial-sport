package com.jason.sport.ui.feature.team.model

import com.jason.domain.entity.Match

data class TeamMatchDataState(
    val matches: MutableList<Match> = mutableListOf(),
    val completeFetching: Boolean = false,
    val errorMsg: String? = null
)