package com.jason.data.repository

import com.jason.data.mapper.toTeam
import com.jason.data.service.TeamService
import com.jason.domain.core.AppResult
import com.jason.domain.entity.Team
import com.jason.domain.repository.ITeamRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@OptIn(FlowPreview::class)
class TeamRepository @Inject constructor(
    private val teamService: TeamService
) : ITeamRepository {
    override fun getTeamList(): Flow<AppResult<List<Team>>> {
        return flow {
            teamService.getTeamList()
                .flowOn(Dispatchers.IO)
                .flatMapMerge {
                    flow {
                        if (it.teams != null) {
                            emit(it.teams.map { it.toTeam() })
                        } else {
                            emit(null)
                        }
                    }
                }
                .catch {
                    emit(AppResult.Failure(Exception(it.message)))
                }
                .collect { response ->
                    if (response != null) {
                        emit(AppResult.Success(response))
                    } else {
                        emit(AppResult.Failure(Exception("null data")))
                    }
                }
        }
    }
}