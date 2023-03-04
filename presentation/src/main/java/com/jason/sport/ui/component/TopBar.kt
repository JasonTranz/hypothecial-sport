package com.jason.sport.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.jason.sport.R
import com.jason.sport.util.DefaultButtonHeight
import com.jason.sport.util.DefaultButtonSmallHeight
import com.jason.sport.util.SmallSpace

@Composable
fun BackTopBar(
    title: String,
    onBackPressed: () -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(DefaultButtonSmallHeight())
                .background(color = MaterialTheme.colorScheme.background)
                .padding(horizontal = SmallSpace()),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ResImage(
                resIconId = androidx.appcompat.R.drawable.abc_ic_ab_back_material,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                modifier = Modifier
                    .size(25.dp)
                    .clickable { onBackPressed.invoke() }
            )
            SemiBoldText(
                modifier = Modifier.padding(start = SmallSpace()),
                content = title
            )
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.secondary),
            thickness = 0.5.dp
        )
    }
}

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    title: String,
    resRightIconId: Int? = null,
    onSearchPressed: (() -> Unit)? = null,
) {
    Column {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(DefaultButtonSmallHeight())
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(horizontal = SmallSpace())
        ) {
            SemiBoldText(
                content = title,
                modifier = Modifier.align(Alignment.Center)
            )
            resRightIconId?.let { resIconId ->
                ResImage(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(30.dp)
                        .clickable { onSearchPressed?.invoke() },
                    resIconId = resIconId,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                )
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.secondary),
            thickness = 0.5.dp
        )
    }
}