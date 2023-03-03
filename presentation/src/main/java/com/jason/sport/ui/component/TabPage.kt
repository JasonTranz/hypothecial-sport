package com.jason.sport.ui.component

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.jason.sport.R
import com.jason.sport.util.DefaultButtonHeight
import com.jason.sport.util.SmallSpace
import kotlinx.coroutines.launch

sealed class TabPageObject(val resLabelId: Int) {
    object Upcoming : TabPageObject(resLabelId = R.string.tab_page_upcoming_title)
    object History : TabPageObject(resLabelId = R.string.tab_page_history_title)
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CustomTabPage(
    pagerState: PagerState,
    tabs: List<TabPageObject>
) {
    val tabIndex = remember { mutableStateOf(pagerState.currentPage) }
    val coroutineScope = rememberCoroutineScope()

    TabRow (
        selectedTabIndex = tabIndex.value,
        indicator = { tabPositions ->
            TabRowIndicator(pagerState = pagerState, tabPositions = tabPositions)
        },
        backgroundColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
    ) {
        tabs.forEachIndexed { index, tabItem ->
            val selectedColor = getSelectedColor(tabIndex.value, index)
            CustomTab(
                modifier = Modifier.padding(horizontal = SmallSpace()),
                selected = tabIndex.value == index,
                onClick = {
                    tabIndex.value = index
                    coroutineScope.launch {
                        pagerState.scrollToPage(tabIndex.value)
                    }
                },
                title = stringResource(id = tabItem.resLabelId),
                tabTitleColor = selectedColor
            )
        }
    }
}

@Composable
fun getSelectedColor(tabIndex: Int, currentIndex: Int): Color {
    return if (tabIndex == currentIndex) {
        MaterialTheme.colorScheme.onBackground
    } else {
        MaterialTheme.colorScheme.secondary
    }
}

@Composable
fun CustomTab(
    selected: Boolean,
    modifier: Modifier = Modifier,
    modifierText: Modifier = Modifier,
    title: String,
    tabHeight: Dp = DefaultButtonHeight(),
    fontSize: TextUnit = 13.sp,
    tabTitleColor: Color,
    onClick: () -> Unit,
    selectedContentColor: Color = LocalContentColor.current,
    unselectedContentColor: Color = selectedContentColor.copy(alpha = 0.5f),
) {
    CustomTransition(selectedContentColor, unselectedContentColor, selected) {
        Column(
            modifier = modifier
                .clickable { onClick() }
                .height(tabHeight),
            verticalArrangement = Arrangement.Center
        ) {
            BoldText(
                modifier = modifierText.fillMaxWidth(),
                content = title,
                fontSize = fontSize,
                color = tabTitleColor,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun TabRowIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    tabPositions: List<TabPosition>,
    indicatorWidth: Dp = 20.dp,
    indicatorHeight: Dp = 2.dp,
    padding: Dp = 20.dp,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Box(
        modifier = modifier
            .pagerTabIndicatorOffset(
                pagerState,
                tabPositions
            )
            .size(
                height = indicatorHeight,
                width = indicatorWidth
            )
            .clip(RoundedCornerShape(2.dp)) // clip modifier not working
            .padding(horizontal = padding)
            .background(color = color)
    )
}

@Composable
fun CustomTransition(
    activeColor: Color,
    inactiveColor: Color,
    selected: Boolean,
    content: @Composable () -> Unit,
) {
    val tabFadeInAnimationDuration = 350
    val tabFadeInAnimationDelay = 300
    val tabFadeOutAnimationDuration = 300

    val transition = updateTransition(selected, label = "")
    val color by transition.animateColor(
        transitionSpec = {
            if (false isTransitioningTo true) {
                tween(
                    durationMillis = tabFadeInAnimationDuration,
                    delayMillis = tabFadeInAnimationDelay,
                    easing = LinearEasing
                )
            } else {
                tween(
                    durationMillis = tabFadeOutAnimationDuration,
                    easing = LinearEasing
                )
            }
        }, label = ""
    ) {
        if (it) activeColor else inactiveColor
    }
    CompositionLocalProvider(
        LocalContentColor provides color.copy(alpha = 1f),
        LocalContentAlpha provides color.alpha,
        content = content
    )
}