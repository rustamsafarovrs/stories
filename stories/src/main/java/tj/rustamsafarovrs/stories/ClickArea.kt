package tj.rustamsafarovrs.stories

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.delay
import kotlin.coroutines.cancellation.CancellationException
import kotlin.random.Random

/**
 * Created by Rustam Safarov on 6/19/25.
 * github.com/rustamsafarovrs
 */

@Composable
internal fun ClickArea(
    currentIndex: Int,
    max: Int,
    config: StoriesDefaults,
    previousClicked: () -> Unit,
    nextClicked: () -> Unit,
    playClicked: () -> Unit,
    pauseClicked: () -> Unit,
) {
    var pauseTriggered = false
    Box(
        Modifier
            .fillMaxSize()
            .pointerInput(currentIndex) {
                detectTapGestures(
                    onPress = {
                        if (config.clickToPauseEnabled == false) {
                            return@detectTapGestures
                        }
                        val released = try {
                            delay(Random.nextInt(150, 300).toLong())
                            pauseClicked.invoke()
                            pauseTriggered = true
                            tryAwaitRelease()
                        } catch (_: CancellationException) {
                            false
                        }
                        if (released) {
                            playClicked.invoke()
                        }
                        pauseTriggered = false
                    },
                    onTap = {
                        if (config.clickToChangeEnabled == false) {
                            return@detectTapGestures
                        }
                        if (pauseTriggered) {
                            return@detectTapGestures
                        }
                        if (it.x < size.width / 2) {
                            if (currentIndex == 0) {
                                return@detectTapGestures
                            }
                            previousClicked.invoke()
                        } else {
                            if (currentIndex >= max - 1) {
                                return@detectTapGestures
                            }
                            nextClicked.invoke()
                        }
                    },
                )
            }
    )
}
