package com.jason.data.service

import com.jason.data.api.TeamAPI
import com.jason.data.entity.TeamListResponse
import com.jason.data.entity.TeamResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import javax.inject.Inject

class TeamService @Inject constructor(
    private val retrofit: Retrofit
): TeamAPI {
    private val api by lazy {
        retrofit.newBuilder().build().create(TeamAPI::class.java)
    }

    override fun getTeamList(): Flow<TeamListResponse> {
        return api.getTeamList()
    }
}