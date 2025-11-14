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

    fun getPrevious(current: Slide): Slide? {
        val index = slides.indexOf(current)
        if (index <= 0 || index > slides.size) {
            return null
        }
        return slides[slides.indexOf(current) - 1]
    }

    fun getNext(current: Slide): Slide? {
        val index = slides.indexOf(current)
        if (index < 0 || index >= slides.size - 1) {
            return null
        }
        return slides[index + 1]
    }

    data class Slide(
        val background: Background,
        val overlay: @Composable () -> Unit = {}
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