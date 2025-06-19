package tj.rustamsafarovrs.stories

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

/**
 * Created by Rustam Safarov on 6/19/25.
 * github.com/rustamsafarovrs
 */

@Composable
internal fun Background(current: StoriesModel.Slide, config: StoriesDefaults) {
    when (current.background) {
        is StoriesModel.Slide.Background.Drawable -> {
            Image(
                painter = painterResource(current.background.id),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        is StoriesModel.Slide.Background.Url -> {
            GlideImage(
                imageModel = current.background.url,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                shimmerParams = ShimmerParams(
                    baseColor = config.shimmerBackgroundColor,
                    highlightColor = config.shimmerHighlightColor,
                    durationMillis = config.shimmerDuration
                ),
            )
        }
    }
}