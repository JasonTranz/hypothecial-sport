package com.jason.domain.repository

import com.jason.domain.core.AppResult
import com.jason.domain.entity.Team
import kotlinx.coroutines.flow.Flow

interface ITeamRepository {
    fun getTeamList(): Flow<AppResult<List<Team>>>
}
