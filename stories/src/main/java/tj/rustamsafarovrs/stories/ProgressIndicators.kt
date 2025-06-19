package tj.rustamsafarovrs.stories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

/**
 * Created by Rustam Safarov on 6/19/25.
 * github.com/rustamsafarovrs
 */

@Composable
internal fun ProgressIndicators(
    count: Int,
    currentIndex: Int,
    progress: Float,
    config: StoriesDefaults
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .padding(horizontal = 20.dp)
            .windowInsetsPadding(WindowInsets.statusBars),
        horizontalArrangement = Arrangement.spacedBy(config.progressSpaceBetween)
    ) {
        (0..<count).forEach {
            LinearProgressIndicator(
                {
                    if (it == currentIndex) {
                        progress
                    } else {
                        if (it < currentIndex) 1f else 0f
                    }
                },
                color = config.progressColor,
                trackColor = config.progressBackgroundColor,
                modifier = Modifier
                    .weight(1f)
                    .height(config.progressHeight)
                    .clip(RoundedCornerShape(config.progressRadius)),
            )
        }
    }
}