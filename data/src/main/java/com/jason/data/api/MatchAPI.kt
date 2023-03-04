package com.jason.data.api

import com.jason.data.entity.MatchCollectionListResponse
import com.jason.data.entity.MatchResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

interface MatchAPI {

    companion object {
        private const val TEAM_ID = "TEAM_ID"
        private const val GET_MATCH_LIST = "teams/matches"
        private const val GET_TEAM_MATCH_LIST = "teams/{${TEAM_ID}}/matches"
    }

    @GET(GET_MATCH_LIST)
    fun getMatchList(): Flow<MatchCollectionListResponse>

    @GET(GET_TEAM_MATCH_LIST)
    fun getTeamMatchList(
        @Path(TEAM_ID) teamId: String
    ): Flow<MatchCollectionListResponse>
}