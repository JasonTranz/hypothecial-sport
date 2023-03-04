package com.jason.sport.ui.feature.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jason.domain.core.AppResult
import com.jason.domain.entity.MatchCollectionList
import com.jason.domain.entity.Match
import com.jason.domain.entity.MatchState
import com.jason.domain.entity.Team
import com.jason.domain.usecase.match.GetMatchListUseCase
import com.jason.domain.usecase.team.GetTeamListUseCase
import com.jason.sport.ui.feature.home.model.MatchListDataState
import com.jason.sport.ui.feature.team.model.TeamListDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMatchListUseCase: GetMatchListUseCase,
    private val getTeamListUseCase: GetTeamListUseCase,
): ViewModel() {

    private val _upcomingMatchListDataState = MutableStateFlow(MatchListDataState())
    val upcomingMatchListDataState = _upcomingMatchListDataState.asStateFlow()

    private val _previousMatchListDataState = MutableStateFlow(MatchListDataState())
    val previousMatchListDataState = _previousMatchListDataState.asStateFlow()

    private val _teamListDataState = MutableStateFlow(TeamListDataState())
    val teamListDataState = _teamListDataState.asStateFlow()

    init {
        getMatchAndTeamList()
    }

    private fun getMatchAndTeamList() {
        var previousMatches: List<Match>
        var upcomingMatches: List<Match>
        var teamList: List<Team>

        viewModelScope.launch {
            combine(
                getMatchListUseCase.invoke(Unit),
                getTeamListUseCase.invoke(Unit)
            ) { matchResponse, teamResponse ->
                previousMatches = handleGetMatchListResponse(matchResponse, MatchState.PREVIOUS.value)
                upcomingMatches = handleGetMatchListResponse(matchResponse, MatchState.UPCOMING.value)

                teamList = handleGetTeamListResponse(teamResponse)

                mapTeamToMatchList(previousMatches, teamList)
                mapTeamToMatchList(upcomingMatches, teamList)

                MatchCollectionList(
                    previousMatches = previousMatches,
                    upcomingMatches = upcomingMatches
                )
            }.flowOn(Dispatchers.IO)
                .collect { matchCollections ->
                    _upcomingMatchListDataState.update { it.copy(matches = matchCollections.upcomingMatches.toMutableList()) }
                    _previousMatchListDataState.update { it.copy(matches = matchCollections.previousMatches.toMutableList()) }
                }
        }
    }

    private fun handleGetMatchListResponse(appResult: AppResult<MatchCollectionList>, type: String): List<Match> {
        return when (appResult) {
            is AppResult.Success -> {
                when(type) {
                    MatchState.PREVIOUS.value -> appResult.data?.previousMatches ?: emptyList()
                    MatchState.UPCOMING.value -> appResult.data?.upcomingMatches ?: emptyList()
                    else -> emptyList()
                }
            }
            is AppResult.Failure -> {
                emptyList()
            }
        }
    }

    private fun handleGetTeamListResponse(appResult: AppResult<List<Team>>): List<Team> {
        return when (appResult) {
            is AppResult.Success -> {
                appResult.data?.let { teams ->
                    _teamListDataState.update { it.copy(teams = teams.toMutableList()) }
                    teams
                } ?: emptyList()
            }
            is AppResult.Failure -> {
                emptyList()
            }
        }
    }

    private fun mapTeamToMatchList(matches: List<Match>, teams: List<Team>) {
        val teamMap = hashMapOf<String, Team>()

        for (i in teams.indices) {
            teamMap[teams[i].name] = teams[i]
        }

        for (i in matches.indices) {
            val homeExisted = teamMap[matches[i].home.name]
            if (homeExisted != null) {
                matches[i].home = homeExisted
            }

            val awayExisted = teamMap[matches[i].away.name]
            if (awayExisted != null) {
                matches[i].away = awayExisted
            }
        }
    }
}