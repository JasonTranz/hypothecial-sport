package com.jason.sport.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.jason.domain.core.NavArgument
import com.jason.domain.core.NavTarget
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

data class NavigationRouteData(
    val route: String = "",
    val isPopBackStack: Boolean = false,
    val popUpToRoute: String? = null
)

@Singleton
class AppNavigator @Inject constructor() {
    private val mutableSharedFlow = MutableSharedFlow<NavigationRouteData>(extraBufferCapacity = 1)
    var sharedFlow = mutableSharedFlow.asSharedFlow()

    fun getRoute(navTarget: NavTarget): String {
        return navTarget.route + getRouteSuffix(navTarget)
    }

    fun goToTeamDetailScreen(teamId: String) {
        navigateTo(
            navTarget = NavTarget.TeamDetailScreen,
            navArguments = listOf(
                navArgument(NavArgument.TEAM_ID) {
                    type = NavType.StringType; defaultValue = teamId
                }
            )
        )
    }

    private fun getRouteSuffix(navTarget: NavTarget): String {
        return when (navTarget) {
            NavTarget.HomeScreen -> "/{${NavArgument.TEAM_ID}}"
            else -> ""
        }
    }

    private fun getRouteWithArguments(
        navTarget: NavTarget,
        navArguments: List<NamedNavArgument>?
    ): String {
        return when(navTarget) {
            is NavTarget.TeamDetailScreen -> navTarget.route + "/${navArguments?.get(0)?.argument?.defaultValue}"
            else -> navTarget.route
        }
    }

    private fun navigateTo(
        navTarget: NavTarget,
        navArguments: List<NamedNavArgument>? = emptyList(),
        popUpToRoute: String? = "",
    ) {
        mutableSharedFlow.tryEmit(
            NavigationRouteData(
                route = getRouteWithArguments(navTarget, navArguments),
                popUpToRoute = popUpToRoute
            )
        )
    }
}