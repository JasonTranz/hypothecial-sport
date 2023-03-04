package com.jason.sport.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.jason.sport.R

@Composable
fun BackTopBar(
    title: String,
    onBackPressed: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(53.dp)
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        SemiBoldText(
            content = title,
            modifier = Modifier.align(Alignment.Center)
        )
        ResImage(
            resIconId = R.drawable.ic_left_arrow,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(40.dp)
                .padding(start = 16.dp)
                .clickable { onBackPressed.invoke() }
        )
    }
}

@Composable
fun HomeTopBar(
    title: String,
    hasRightIcon: Boolean = false,
    resRightIconId: Int = R.drawable.ic_left_arrow,
    onSearchPressed: (() -> Unit)? = null,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(53.dp)
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        SemiBoldText(
            content = title,
            modifier = Modifier.align(Alignment.Center)
        )
        if (hasRightIcon) {
            ResImage(
                resIconId = resRightIconId,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(30.dp)
                    .padding(start = 16.dp)
                    .clickable { onSearchPressed?.invoke() }
            )
        }
    }
}