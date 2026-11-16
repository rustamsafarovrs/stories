package tj.rustamsafarovrs.stories

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable

/**
 * Created by Rustam Safarov on 6/18/25.
 * github.com/rustamsafarovrs
 */

data class StoriesModel(
    val slides: List<Slide>,
    val config: StoriesDefaults = StoriesDefaults(),
    val initialSlideIndex: Int = 0,
) {
    init {
        require(slides.isNotEmpty()) { "StoriesModel must contain at least one slide." }
        require(initialSlideIndex in slides.indices) {
            "initialSlideIndex must be a valid index in slides."
        }
        require(config.slideDurationMs > 0L) {
            "slideDurationMs must be greater than zero."
        }
    }

    /**
     * Returns the previous slide index, or null when [currentIndex] is invalid
     * or already points to the first slide.
     */
    fun getPreviousIndex(currentIndex: Int): Int? =
        if (currentIndex in slides.indices) currentIndex.takeIf { it > 0 }?.minus(1) else null

    /**
     * Returns the next slide index, or null when [currentIndex] is invalid
     * or already points to the last slide.
     */
    fun getNextIndex(currentIndex: Int): Int? =
        if (currentIndex in slides.indices) (currentIndex + 1).takeIf { it in slides.indices } else null

    /**
     * Compatibility helpers for callers that navigate by slide instance.
     * Identity is checked first so equal slides at different positions remain distinguishable.
     */
    fun getPrevious(current: Slide): Slide? {
        val index = indexOf(current)
        return if (index >= 0) slides.getOrNull(index - 1) else null
    }

    fun getNext(current: Slide): Slide? {
        val index = indexOf(current)
        return if (index >= 0) slides.getOrNull(index + 1) else null
    }

    private fun indexOf(current: Slide): Int {
        val identityIndex = slides.indexOfFirst { it === current }
        return if (identityIndex >= 0) identityIndex else slides.indexOf(current)
    }

    data class Slide(
        val background: Background,
        val overlay: @Composable () -> Unit = {},
    ) {
        sealed class Background {
            data class Drawable(@DrawableRes val id: Int) : Background()
            data class Url(val url: String) : Background()
        }
    }
}

fun drawableBackground(@DrawableRes id: Int): StoriesModel.Slide.Background.Drawable {
    return StoriesModel.Slide.Background.Drawable(id)
}

fun urlBackground(url: String): StoriesModel.Slide.Background.Url {
    return StoriesModel.Slide.Background.Url(url)
}
