package com.jason.sport.ui.component

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
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.runtime.Composable
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
import com.jason.sport.util.WindowUtil
import kotlinx.coroutines.launch

sealed class TabPageObject(val resLabelId: Int) {

}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun TabPage(
    pagerState: PagerState,
    tabs: List<TabPageObject>
) {
    val tabIndex = remember { mutableStateOf(pagerState.currentPage) }
    val coroutineScope = rememberCoroutineScope()
//
//    fun getSelectedColor(currentIndex: Int): Color {
//        return if (tabIndex.value == )
//    }

    TabRow (
        selectedTabIndex = tabIndex.value,
        indicator = { tabPositions ->
            TabRowIndicator(pagerState = pagerState, tabPositions = tabPositions)
        },
        backgroundColor = MaterialTheme.colors.primary
    ) {
        tabs.forEachIndexed { index, tabItem ->
            CustomTab(
                modifier = Modifier.padding(horizontal = 16.dp),
                selected = tabIndex.value == index,
                onClick = {
                    tabIndex.value = index
                    coroutineScope.launch {
                        pagerState.scrollToPage(tabIndex.value)
                    }
                },
                title = stringResource(id = tabItem.resLabelId),
                tabTitleColor = Color.White
            )
        }
    }
}

@Composable
fun CustomTab(
    selected: Boolean,
    modifier: Modifier = Modifier,
    modifierText: Modifier = Modifier,
    title: String,
    tabHeight: Dp = WindowUtil.GetStatusBarHeight(),
    fontSize: TextUnit = 12.sp,
    tabTitleColor: Color,
    onClick: () -> Unit,
    selectedContentColor: Color = LocalContentColor.current,
    unselectedContentColor: Color = selectedContentColor.copy(alpha = 0.5f),
) {
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

@ExperimentalPagerApi
@Composable
fun TabRowIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    tabPositions: List<TabPosition>,
    indicatorWidth: Dp = 20.dp,
    indicatorHeight: Dp = 2.dp,
    padding: Dp = 20.dp,
    color: Color = MaterialTheme.colors.primary
) {
    Box(
        modifier = modifier
            .pagerTabIndicatorOffset(
                pagerState,
                tabPositions
            )
            .size(height = indicatorHeight, width = indicatorWidth)
            .clip(RoundedCornerShape(2.dp)) // clip modifier not working
            .padding(horizontal = padding)
            .background(color = color)
    )
}