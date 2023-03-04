package com.jason.sport.ui.feature.team.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jason.domain.core.AppResult
import com.jason.domain.entity.Match
import com.jason.domain.entity.MatchCollectionList
import com.jason.domain.entity.MatchState
import com.jason.domain.entity.Team
import com.jason.domain.usecase.match.GetTeamMatchListUseCase
import com.jason.domain.usecase.team.GetTeamListUseCase
import com.jason.sport.ui.feature.team.model.TeamMatchDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamViewModel @Inject constructor(
    private val getTeamMatchListUseCase: GetTeamMatchListUseCase
) : ViewModel() {

    private val _upcomingTeamMatchDataState = MutableStateFlow(TeamMatchDataState())
    val upcomingTeamMatchDataState = _upcomingTeamMatchDataState.asStateFlow()

    private val _previousTeamMatchDataState = MutableStateFlow(TeamMatchDataState())
    val previousTeamMatchDataState = _previousTeamMatchDataState.asStateFlow()

    fun getMatchesOfTeam(
        teamId: String,
        teams: List<Team>
    ) {
        viewModelScope.launch {
            _upcomingTeamMatchDataState.update { it.copy(completeFetching = false) }
            _previousTeamMatchDataState.update { it.copy(completeFetching = false) }
            getTeamMatchListUseCase.invoke(teamId)
                .flowOn(Dispatchers.IO)
                .collect {
                    handleGetMatchesOfTeamResponse(
                        it,
                        teams
                    )
                }
        }
    }

    private fun handleGetMatchesOfTeamResponse(
        appResult: AppResult<MatchCollectionList>,
        teams: List<Team>
    ) {
        when (appResult) {
            is AppResult.Success -> {
                appResult.data?.let { matches ->
                    mapTeamToMatchList(
                        matches.upcomingMatches,
                        teams
                    )
                    mapTeamToMatchList(
                        matches.previousMatches,
                        teams
                    )

                    _upcomingTeamMatchDataState.update {
                        it.copy(
                            matches = matches.upcomingMatches.toMutableList(),
                            completeFetching = true
                        )
                    }
                    _previousTeamMatchDataState.update {
                        it.copy(
                            matches = matches.previousMatches.toMutableList(),
                            completeFetching = true
                        )
                    }
                }
            }

            is AppResult.Failure -> {
                _upcomingTeamMatchDataState.update { it.copy(errorMsg = appResult.message) }
            }
        }
    }

    private fun mapTeamToMatchList(
        matches: List<Match>,
        teams: List<Team>
    ) {
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

    fun releaseData() {
        _upcomingTeamMatchDataState.update { it.copy(matches = mutableListOf(), completeFetching = false) }
        _previousTeamMatchDataState.update { it.copy(matches = mutableListOf(), completeFetching = false) }
    }
}