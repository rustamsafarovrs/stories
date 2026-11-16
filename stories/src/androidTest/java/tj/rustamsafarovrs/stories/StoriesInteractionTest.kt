package tj.rustamsafarovrs.stories

import androidx.compose.material3.Text
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.click
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performTouchInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StoriesInteractionTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun rightTap_advancesToNextSlide() {
        composeRule.setContent {
            Stories(
                model = StoriesModel(
                    slides = listOf(
                        slide("First"),
                        slide("Second"),
                    ),
                    config = StoriesDefaults(slideDurationMs = 60_000L),
                ),
            )
        }

        composeRule.tapOnRightSide()

        composeRule.onNodeWithText("Second").assertIsDisplayed()
    }

    @Test
    fun rightTapOnLastSlide_finishesStories() {
        var finishCount = 0
        composeRule.setContent {
            Stories(
                model = StoriesModel(
                    slides = listOf(slide("Only")),
                    config = StoriesDefaults(slideDurationMs = 60_000L),
                ),
                didFinish = { finishCount++ },
            )
        }

        composeRule.tapOnRightSide()

        composeRule.runOnIdle { assertEquals(1, finishCount) }
    }

    private fun slide(label: String) = StoriesModel.Slide(
        background = drawableBackground(R.drawable.sample_slide_0),
        overlay = { Text(label) },
    )

    private fun ComposeContentTestRule.tapOnRightSide() {
        onRoot().performTouchInput {
            click(Offset(size.width * 0.75f, size.height / 2f))
        }
    }
}
