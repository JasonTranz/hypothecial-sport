package com.jason.sport.ui.feature.match.view

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.jason.sport.ui.navigation.AppNavigator
import com.jason.sport.util.decodeUrl

@Composable
fun MediaPlayerScreen(
    navigator: AppNavigator,
    mediaUrl: String
) {
    val context = LocalContext.current
    val exoPlayer = ExoPlayer.Builder(context).build()
    val mediaItem = MediaItem.fromUri(Uri.parse(mediaUrl.decodeUrl()))
    exoPlayer.setMediaItem(mediaItem)

    val playerView = StyledPlayerView(context)
    playerView.player = exoPlayer
    playerView.setFullscreenButtonClickListener {
        if (it) {
            playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
        } else {
            playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
        }
    }

    fun popBackStack() {
        navigator.popBackStack()
    }

    DisposableEffect(
        AndroidView(
            factory = { playerView }
        )
    ) {
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true

        onDispose {
            exoPlayer.release()
        }
    }
}