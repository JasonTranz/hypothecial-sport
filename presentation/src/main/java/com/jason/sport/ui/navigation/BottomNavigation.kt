package com.jason.sport.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.jason.sport.R
import com.jason.sport.ui.component.BoldText
import com.jason.sport.util.DefaultButtonHeight
import com.jason.sport.util.SystemUtil
import kotlinx.coroutines.launch

sealed class BottomNavItem(
    var resIdTitle: Int,
    var icon: Int,
    var route: String
) {
    object Home : BottomNavItem(
        R.string.navigation_bar_home_title,
        R.drawable.ic_home,
        "home"
    )

    object Team : BottomNavItem(
        R.string.navigation_bar_team_title,
        R.drawable.ic_team,
        "team"
    )

    object Favorite : BottomNavItem(
        R.string.navigation_bar_favorite_title,
        R.drawable.ic_star_border,
        "favorite"
    )

    object Menu : BottomNavItem(
        R.string.navigation_bar_menu_title,
        R.drawable.ic_menu,
        "menu"
    )
}

@ExperimentalPagerApi
@Composable
fun CustomBottomNavigation(
    items: List<BottomNavItem>,
    pagerState: PagerState
) {
    val scope = rememberCoroutineScope()
    Column {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary),
            thickness = 0.5.dp
        )
        BottomNavigation(
            contentColor = Color.Black,
            backgroundColor = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
                .clip(
                    RoundedCornerShape(
                        topStart = 15.dp,
                        topEnd = 15.dp
                    )
                )
                .height(DefaultButtonHeight() + SystemUtil.getBottomNavigationBarHeight())
        ) {
            items.forEachIndexed { index, item ->
                val selected = index == pagerState.currentPage
                BottomNavigationItem(
                    modifier = Modifier.navigationBarsPadding(),
                    icon = {
                        Icon(
                            painterResource(id = item.icon),
                            contentDescription = stringResource(id = item.resIdTitle),
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    label = {
                        BoldText(
                            content = stringResource(id = item.resIdTitle),
                            fontSize = 10.sp,
                            color = if (selected) {
                                MaterialTheme.colorScheme.onBackground
                            } else {
                                MaterialTheme.colorScheme.secondary
                            }
                        )
                    },
                    selectedContentColor = MaterialTheme.colorScheme.onBackground,
                    unselectedContentColor = MaterialTheme.colorScheme.secondary,
                    alwaysShowLabel = true,
                    selected = selected,
                    onClick = {
                        scope.launch {
                            pagerState.scrollToPage(index)
                        }
                    }
                )
            }
        }
    }
}