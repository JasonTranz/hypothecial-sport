package com.jason.data.service

import com.jason.data.api.MatchAPI
import com.jason.data.entity.MatchResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import javax.inject.Inject

class MatchService @Inject constructor(
    private val retrofit: Retrofit
): MatchAPI {

    private val api by lazy {
        retrofit.newBuilder().build().create(MatchAPI::class.java)
    }

    override fun getMatchList(): Flow<List<MatchResponse>> {
        return api.getMatchList()
    }

    override fun getTeamMatchList(teamId: String): Flow<List<MatchResponse>> {
        return api.getTeamMatchList(teamId)
    }
}