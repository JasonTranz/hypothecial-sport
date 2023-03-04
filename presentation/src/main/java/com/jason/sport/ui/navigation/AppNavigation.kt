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
import com.jason.sport.ui.feature.match.view.MediaPlayerScreen
import com.jason.sport.ui.feature.splash.view.SplashScreen
import com.jason.sport.ui.feature.splash.viewModel.SplashViewModel
import com.jason.sport.ui.feature.team.view.TeamDetailScreen
import com.jason.sport.ui.feature.team.viewModel.TeamViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation(
    navigator: AppNavigator,
    splashViewModel: SplashViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(),
    teamViewModel: TeamViewModel = hiltViewModel()
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

    val splashRoute = navigator.getRoute(NavTarget.SplashScreen)
    val homeRoute = navigator.getRoute(NavTarget.HomeScreen)
    val teamListRoute = navigator.getRoute(NavTarget.TeamListScreen)
    val teamDetailRoute = navigator.getRoute(NavTarget.TeamDetailScreen)
    val mediaPlayerRoute = navigator.getRoute(NavTarget.MediaPlayerScreen)

    AnimatedNavHost(
        navController = navController,
        startDestination = splashRoute
    ) {
        composable(
            route = splashRoute
        ) {
            SplashScreen(
                navigator = navigator,
                splashViewModel = splashViewModel
            )
        }

        composable(
            route = homeRoute,
            enterTransition = {
                enterTransition(listOf(splashRoute))
            },
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
            route = teamListRoute,
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
                teamId = teamId,
                teamViewModel = teamViewModel,
                homeViewModel = homeViewModel
            )
        }

        composable(
            route = mediaPlayerRoute,
            enterTransition = {
                enterTransition(listOf(homeRoute))
            }
        ) { navBackStackEntry ->
            val mediaUrl = navBackStackEntry.arguments?.getString(NavArgument.MEDIA_URL) ?: AppConstant.UNDEFINED

            MediaPlayerScreen(
                navigator = navigator,
                mediaUrl = mediaUrl
            )
        }
    }
}