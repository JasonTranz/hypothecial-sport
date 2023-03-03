package com.jason.data.api

import com.jason.data.entity.TeamResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.POST

interface TeamAPI {

    companion object {
        private const val GET_TEAM_LIST = "teams"
    }

    @POST(GET_TEAM_LIST)
    fun getTeamList(): Flow<List<TeamResponse>>
}