package com.jason.sport.ui.feature.team.view.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.jason.domain.entity.Team
import com.jason.sport.R
import com.jason.sport.ui.component.ResImage
import com.jason.sport.ui.feature.match.view.item.IconText

@Composable
fun TeamItemView(
    modifier: Modifier = Modifier,
    team: Team,
    onItemPressed: (Team) -> Unit
) {
    val favoriteResIconId = remember { mutableStateOf(R.drawable.ic_star_border) }

    LaunchedEffect(team) {
        favoriteResIconId.value = getResFavoriteIconId(team.isFavorite)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 10.dp,
                horizontal = 16.dp
            ),
        backgroundColor = MaterialTheme.colorScheme.background,
        shape = RoundedCornerShape(8.dp),
        elevation = 1.dp
    ) {
        ConstraintLayout(
            modifier = modifier
                .clickable { onItemPressed.invoke(team) }
                .padding(
                    vertical = 10.dp,
                    horizontal = 16.dp
                )
                .fillMaxWidth()
        ) {
            val (textIcon, favorite) = createRefs()

            IconText(
                title = team.name,
                url = team.logo,
                modifier = Modifier.constrainAs(textIcon) {
                    centerVerticallyTo(parent)
                    start.linkTo(parent.start)
                }
            )

            ResImage(
                resIconId = favoriteResIconId.value,
                modifier = Modifier
                    .size(15.dp)
                    .constrainAs(favorite) {
                        centerVerticallyTo(parent)
                        end.linkTo(parent.end)
                    }
            )
        }
    }
}

private fun getResFavoriteIconId(isFavorite: Boolean): Int {
    return if (isFavorite) {
        R.drawable.ic_star_filled
    } else {
        R.drawable.ic_star_border
    }
}