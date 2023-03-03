package com.jason.data.mapper

import com.jason.data.entity.TeamResponse
import com.jason.domain.entity.Team

fun TeamResponse.toTeam(): Team {
    return Team(
        id = this.id ?: "",
        name = this.name ?: "",
        logo = this.logo ?: ""
    )
}