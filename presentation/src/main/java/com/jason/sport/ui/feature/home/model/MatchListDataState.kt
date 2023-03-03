package com.jason.sport.ui.feature.home.model

import com.jason.domain.entity.Match

data class MatchListDataState(
    val matches: MutableList<Match> = mutableListOf(),
    val loading: Boolean = false,
    val errorMsg: String? = null
)
