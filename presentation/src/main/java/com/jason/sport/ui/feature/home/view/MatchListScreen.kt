package com.jason.sport.ui.feature.home.view

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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.jason.domain.entity.Match
import com.jason.sport.ui.component.CustomTabPage
import com.jason.sport.ui.component.TabPageObject
import com.jason.sport.ui.feature.home.view.item.MatchItemView
import com.jason.sport.ui.feature.home.viewModel.HomeViewModel
import com.jason.sport.ui.navigation.AppNavigator

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MatchListScreen(
    navigator: AppNavigator,
    homeViewModel: HomeViewModel
) {
    val upcomingMatchListDataViewModelState by remember(homeViewModel) { homeViewModel.upcomingMatchListDataState }.collectAsState()
    val previousMatchListDataViewModelState by remember(homeViewModel) { homeViewModel.previousMatchListDataState }.collectAsState()

    val upcomingMatches = upcomingMatchListDataViewModelState.matches
    val historyMatches = previousMatchListDataViewModelState.matches

    val tabs = listOf(
        TabPageObject.Upcoming,
        TabPageObject.History
    )

    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))
    ) {
        CustomTabPage(
            pagerState = pagerState,
            tabs = tabs
        )

        HorizontalPager(
            count = tabs.size,
            state = pagerState
        ) { index ->
            when (index) {
                0 -> {
                    UpcomingMatches(
                        matches = upcomingMatches
                    )
                }
                1 -> {
                    HistoryMatches(
                        navigator = navigator,
                        matches = historyMatches
                    )
                }
            }
        }
    }
}

@Composable
fun UpcomingMatches(
    matches: List<Match>
) {
    LazyColumn {
        items(
            count = matches.size,
            key = { it },
            itemContent = {
                MatchItemView(match = matches[it])
            }
        )
    }
}

@Composable
fun HistoryMatches(
    navigator: AppNavigator,
    matches: List<Match>
) {
    LazyColumn {
        items(
            count = matches.size,
            key = { it },
            itemContent = {
                MatchItemView(match = matches[it])
            }
        )
    }
}