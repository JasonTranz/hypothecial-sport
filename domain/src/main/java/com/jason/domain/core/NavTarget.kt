package com.jason.domain.core

sealed class NavTarget(val route: String) {

    object SplashScreen : NavTarget(route = "SplashScreen")

    object HomeScreen : NavTarget(route = "HomeScreen")

    object TeamDetailScreen : NavTarget(route = "TeamDetailScreen")
}