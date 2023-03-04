package com.jason.sport.ui.feature.team.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.jason.domain.entity.Team
import com.jason.sport.R
import com.jason.sport.ui.component.HomeTopBar
import com.jason.sport.ui.feature.home.viewModel.HomeViewModel
import com.jason.sport.ui.feature.team.view.item.TeamItemView
import com.jason.sport.ui.navigation.AppNavigator

@Composable
fun TeamListScreen(
    navigator: AppNavigator,
    homeViewModel: HomeViewModel
) {
    val teamListDataState by remember { homeViewModel.teamListDataState }.collectAsState()
    val teams = teamListDataState.teams

    fun onItemPressed(team: Team) {
        navigator.goToTeamDetailScreen(teamId = team.id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))
    ) {
        HomeTopBar(
            title = stringResource(id = R.string.team_title),
            resRightIconId = androidx.appcompat.R.drawable.abc_ic_search_api_material
        )

        LazyColumn {
            items(
                count = teams.size,
                key = { it },
                itemContent = { index ->
                    TeamItemView(
                        team = teams[index],
                        onItemPressed = { onItemPressed(it) }
                    )
                }
            )
        }
    }
}