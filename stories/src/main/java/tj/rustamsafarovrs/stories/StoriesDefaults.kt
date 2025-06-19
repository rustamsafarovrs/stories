package tj.rustamsafarovrs.stories

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Created by Rustam Safarov on 6/19/25.
 * github.com/rustamsafarovrs
 */

data class StoriesDefaults(
    val slideDurationMs: Long = 3_000L,
    val progressSpaceBetween: Dp = 8.dp,
    val progressBackgroundColor: Color = Color.LightGray,
    val progressColor: Color = Color.Blue,
    val progressHeight: Dp = 4.dp,
    val progressRadius: Dp = 2.dp,
    val clickToChangeEnabled: Boolean = true,
    val clickToPauseEnabled: Boolean = true,
    val shimmerBackgroundColor: Color = Color.White,
    val shimmerHighlightColor: Color = Color.LightGray,
    val shimmerDuration: Int = 1000,
)