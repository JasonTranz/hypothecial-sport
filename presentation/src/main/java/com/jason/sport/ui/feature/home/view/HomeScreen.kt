package com.jason.sport.ui.feature.home.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.jason.sport.ui.component.RegularText
import com.jason.sport.ui.feature.home.viewModel.HomeViewModel
import com.jason.sport.ui.feature.team.view.TeamListScreen
import com.jason.sport.ui.navigation.AppNavigator
import com.jason.sport.ui.navigation.BottomNavItem
import com.jason.sport.ui.navigation.CustomBottomNavigation

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    navigator: AppNavigator,
    homeViewModel: HomeViewModel
) {
    val pagerState = rememberPagerState()

    val navigationItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Team,
        BottomNavItem.Favorite,
        BottomNavItem.Menu
    )

    BackHandler(false) { }

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        bottomBar = {
            CustomBottomNavigation(
                items = navigationItems,
                pagerState = pagerState
            )
        }
    ) { paddingValues ->
        HorizontalPager(
            count = navigationItems.size,
            state = pagerState,
            modifier = Modifier.padding(paddingValues),
            userScrollEnabled = false
        ) { index ->
            when (index) {
                0 -> {
                    MatchListScreen(
                        navigator = navigator,
                        homeViewModel = homeViewModel
                    )
                }
                1 -> {
                    TeamListScreen(
                        navigator = navigator,
                        homeViewModel = homeViewModel
                    )
                }
                2 -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        RegularText(content = "Favorite")
                    }
                }
                3 -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        RegularText(content = "Menu")
                    }
                }
            }
        }
    }
}