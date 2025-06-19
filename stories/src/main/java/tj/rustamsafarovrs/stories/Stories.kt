package tj.rustamsafarovrs.stories

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
    didFinish: () -> Unit = {},
) {
    var current by remember { mutableStateOf(model.slides[0]) }
    var progress by remember { mutableLongStateOf(0L) }
    var slideState by remember { mutableStateOf<SlideState>(SlideState.Play) }

    LaunchedEffect(current) {
        progress = 0L
        while (progress < model.config.slideDurationMs) {
            delay(50L)
            if (slideState == SlideState.Play) {
                progress = progress + 50L
            }
        }
        if (progress >= model.config.slideDurationMs) {
            val next = model.slides.indexOf(current) + 1
            if (next < model.slides.size) {
                current = model.slides[next]
            } else {
                didFinish()
            }
        }
    }

    Box(Modifier.fillMaxSize()) {
        Background(current, model.config)
        ProgressIndicators(
            count = model.slides.size,
            currentIndex = model.slides.indexOf(current),
            progress = (progress.toFloat() * 100 / model.config.slideDurationMs.toFloat()) / 100,
            config = model.config,
        )
        ClickArea(
            currentIndex = model.slides.indexOf(current),
            max = model.slides.size,
            config = model.config,
            previousClicked = { current = model.getPrevious(current)!! },
            nextClicked = { current = model.getNext(current)!! },
            playClicked = { slideState = SlideState.Play },
            pauseClicked = { slideState = SlideState.Pause },
        )
        current.overlay()
    }
}
