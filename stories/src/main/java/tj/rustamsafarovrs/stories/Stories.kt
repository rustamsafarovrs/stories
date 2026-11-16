package tj.rustamsafarovrs.stories

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

/**
 * Created by Rustam Safarov on 6/18/25.
 * github.com/rustamsafarovrs
 */

@Composable
fun Stories(
    model: StoriesModel,
    controller: StoriesController = remember { StoriesController() },
    didFinish: () -> Unit = {},
) {
    var currentIndex by remember(model) {
        mutableIntStateOf(model.initialSlideIndex)
    }
    var progress by remember(model, currentIndex) {
        mutableLongStateOf(0L)
    }
    var slideState by remember(controller) {
        mutableStateOf<SlideState>(SlideState.Play)
    }

    // Keep callbacks connected to the latest state without capturing stale values.
    SideEffect {
        controller.next = {
            val nextIndex = model.getNextIndex(currentIndex)
            if (nextIndex != null) {
                currentIndex = nextIndex
            } else {
                didFinish()
            }
        }
        controller.previous = {
            model.getPreviousIndex(currentIndex)?.let { currentIndex = it }
        }
        controller.play = { slideState = SlideState.Play }
        controller.pause = { slideState = SlideState.Pause }
    }

    // A controller can outlive this composition; never leave callbacks pointing at it.
    DisposableEffect(controller) {
        onDispose { controller.clearCallbacks() }
    }

    LaunchedEffect(model, currentIndex, slideState) {
        if (slideState == SlideState.Play) {
            val duration = model.config.slideDurationMs
            while (progress < duration) {
                val elapsed = minOf(50L, duration - progress)
                delay(elapsed)
                progress = (progress + elapsed).coerceAtMost(duration)
            }
            controller.goNext()
        }
    }

    val current = model.slides[currentIndex]

    Box(Modifier.fillMaxSize()) {
        Background(current, model.config)
        ProgressIndicators(
            count = model.slides.size,
            currentIndex = currentIndex,
            progress = (progress.toFloat() / model.config.slideDurationMs.toFloat())
                .coerceIn(0f, 1f),
            config = model.config,
        )
        ClickArea(
            config = model.config,
            isPlaying = slideState == SlideState.Play,
            previousClicked = { controller.goPrevious() },
            nextClicked = { controller.goNext() },
            playClicked = { controller.doPlay() },
            pauseClicked = { controller.doPause() },
        )
        current.overlay()
    }
}
