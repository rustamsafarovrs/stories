package tj.rustamsafarovrs.stories

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput

/**
 * Created by Rustam Safarov on 6/19/25.
 * github.com/rustamsafarovrs
 */

@Composable
internal fun ClickArea(
    config: StoriesDefaults,
    isPlaying: Boolean,
    previousClicked: () -> Unit,
    nextClicked: () -> Unit,
    playClicked: () -> Unit,
    pauseClicked: () -> Unit,
) {
    val latestConfig by rememberUpdatedState(config)
    val latestIsPlaying by rememberUpdatedState(isPlaying)
    val latestPreviousClicked by rememberUpdatedState(previousClicked)
    val latestNextClicked by rememberUpdatedState(nextClicked)
    val latestPlayClicked by rememberUpdatedState(playClicked)
    val latestPauseClicked by rememberUpdatedState(pauseClicked)

    Box(
        Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                var resumeAfterLongPress = false

                detectTapGestures(
                    onPress = {
                        val wasPlaying = latestIsPlaying
                        try {
                            tryAwaitRelease()
                        } finally {
                            if (resumeAfterLongPress) {
                                resumeAfterLongPress = false
                                if (wasPlaying) latestPlayClicked()
                            }
                        }
                    },
                    onLongPress = {
                        // A hold pauses only active playback. A manually paused story
                        // must remain paused after the pointer is released.
                        if (latestConfig.clickToPauseEnabled && latestIsPlaying) {
                            resumeAfterLongPress = true
                            latestPauseClicked()
                        }
                    },
                    onTap = { offset ->
                        if (!latestConfig.clickToChangeEnabled) {
                            return@detectTapGestures
                        }

                        if (offset.x < size.width / 2) {
                            latestPreviousClicked()
                        } else {
                            latestNextClicked()
                        }
                    },
                )
            },
    )
}
