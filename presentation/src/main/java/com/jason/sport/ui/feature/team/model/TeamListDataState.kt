package com.jason.sport.ui.feature.team.model

import com.jason.domain.entity.Team

data class TeamListDataState(
    val teams: MutableList<Team> = mutableListOf(),
    val loading: Boolean = false,
    val errorMsg: String? = null
)
