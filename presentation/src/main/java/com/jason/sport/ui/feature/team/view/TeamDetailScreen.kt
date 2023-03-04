package com.jason.sport.ui.feature.team.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.jason.sport.R
import com.jason.sport.ui.component.BackTopBar
import com.jason.sport.ui.component.BoldText
import com.jason.sport.ui.feature.match.view.item.MatchItemView
import com.jason.sport.ui.feature.home.viewModel.HomeViewModel
import com.jason.sport.ui.feature.team.viewModel.TeamViewModel
import com.jason.sport.ui.navigation.AppNavigator
import com.jason.sport.util.SmallSpace
import dagger.Lazy
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TeamDetailScreen(
    navigator: AppNavigator,
    teamId: String,
    teamViewModel: TeamViewModel,
    homeViewModel: HomeViewModel
) {
    val coroutineScope = rememberCoroutineScope()

    val upcomingTeamMatchDataState by remember { teamViewModel.upcomingTeamMatchDataState }.collectAsState()
    val previousTeamMatchDataState by remember { teamViewModel.previousTeamMatchDataState }.collectAsState()
    val teamListDataState by remember { homeViewModel.teamListDataState }.collectAsState()

    val teamName = remember { mutableStateOf("") }

    val teams = teamListDataState.teams
    val upcomingMatches = upcomingTeamMatchDataState.matches
    val historyMatches = previousTeamMatchDataState.matches

    LaunchedEffect(teams) {
        if (teams.isNotEmpty()) {
            teamName.value = teams.find { it.id == teamId }?.name ?: ""
        }
    }

    LaunchedEffect(teamId) {
        teamViewModel.getMatchesOfTeam(
            teamId = teamId,
            teams = teamListDataState.teams
        )
    }

    fun popBackStack() {
        navigator.popBackStack()
        coroutineScope.launch {
            delay(800)
            teamViewModel.releaseData()
            coroutineScope.cancel()
        }
    }

    BackHandler { popBackStack() }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        BackTopBar(
            title = teamName.value,
            onBackPressed = { popBackStack() }
        )

        LazyColumn {
            item {
                Spacer(modifier = Modifier.height(SmallSpace()))
            }

            item {
                if (upcomingMatches.isNotEmpty()) {
                    BoldText(
                        content = stringResource(id = R.string.tab_page_upcoming_title),
                        modifier = Modifier.padding(horizontal = SmallSpace()),
                        fontSize = 13.sp
                    )
                }
            }
            
            items(
                count = upcomingMatches.size,
                key = { it },
                itemContent = {
                    MatchItemView(match = upcomingMatches[it])
                }
            )

            item {
                if (historyMatches.isNotEmpty()) {
                    BoldText(
                        content = stringResource(id = R.string.tab_page_history_title),
                        modifier = Modifier.padding(horizontal = SmallSpace()),
                        fontSize = 13.sp
                    )
                }
            }

            items(
                count = historyMatches.size,
                itemContent = {
                    MatchItemView(match = historyMatches[it])
                }
            )
        }
    }
}