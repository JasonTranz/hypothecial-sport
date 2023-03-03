package com.jason.sport.util

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

object WindowUtil {

    @Composable
    fun GetStatusBarHeight(): Dp {
        return WindowInsets.systemBars.asPaddingValues().calculateTopPadding()
    }
}