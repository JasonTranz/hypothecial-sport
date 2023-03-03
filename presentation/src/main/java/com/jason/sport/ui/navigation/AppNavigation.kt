package com.jason.sport.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.jason.domain.core.NavArgument
import com.jason.domain.core.NavTarget
import com.jason.sport.common.AppConstant
import com.jason.sport.ui.component.enterTransition
import com.jason.sport.ui.component.popEnterTransition
import com.jason.sport.ui.feature.home.view.HomeScreen
import com.jason.sport.ui.feature.home.viewModel.HomeViewModel
import com.jason.sport.ui.feature.team.view.TeamDetailScreen
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation(
    navigator: AppNavigator,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val navController = rememberAnimatedNavController()

    LaunchedEffect(true) {
        navigator.sharedFlow.onEach { routeData ->
            if (routeData.isPopBackStack) {
                if (routeData.route.isEmpty()) {
                    navController.popBackStack()
                } else {
                    navController.popBackStack(route = routeData.route, inclusive = false)
                }
            } else {
                navController.navigate(routeData.route) {
                    routeData.popUpToRoute?.let { popUpTo(it) }
                }
            }
        }.launchIn(this)
    }

    val homeRoute = navigator.getRoute(NavTarget.HomeScreen)
    val teamDetailRoute = navigator.getRoute(NavTarget.TeamDetailScreen)

    AnimatedNavHost(
        navController = navController,
        startDestination = homeRoute
    ) {
        composable(
            route = homeRoute,
            popEnterTransition = {
                popEnterTransition(listOf(teamDetailRoute))
            }
        ) {
            HomeScreen(
                navigator = navigator,
                homeViewModel = homeViewModel
            )
        }

        composable(
            route = teamDetailRoute,
            enterTransition = {
                enterTransition(listOf(homeRoute))
            }
        ) { navBackStackEntry ->
            val teamId = navBackStackEntry.arguments?.getString(NavArgument.TEAM_ID) ?: AppConstant.UNDEFINED

            TeamDetailScreen(
                navigator = navigator,
                teamId = teamId
            )
        }
    }
}