package tj.rustamsafarovrs.stories

import androidx.compose.runtime.Composable
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Test

class StoriesModelTest {

    private fun slide(url: String) =
        StoriesModel.Slide(background = StoriesModel.Slide.Background.Url(url))

    @Test
    fun getNextIndex_returnsFollowingIndex() {
        val model = StoriesModel(listOf(slide("1"), slide("2"), slide("3")))

        assertEquals(1, model.getNextIndex(0))
        assertEquals(2, model.getNextIndex(1))
        assertNull(model.getNextIndex(2))
    }

    @Test
    fun getPreviousIndex_returnsPreviousIndex() {
        val model = StoriesModel(listOf(slide("1"), slide("2"), slide("3")))

        assertEquals(1, model.getPreviousIndex(2))
        assertEquals(0, model.getPreviousIndex(1))
        assertNull(model.getPreviousIndex(0))
    }

    @Test
    fun indexNavigation_rejectsOutOfRangeIndexes() {
        val model = StoriesModel(listOf(slide("1"), slide("2")))

        assertNull(model.getNextIndex(-1))
        assertNull(model.getNextIndex(2))
        assertNull(model.getPreviousIndex(-1))
        assertNull(model.getPreviousIndex(2))
    }

    @Test
    fun indexNavigation_handlesEqualSlidesAtDifferentPositions() {
        val sharedOverlay: @Composable () -> Unit = {}
        val first = StoriesModel.Slide(StoriesModel.Slide.Background.Url("same"), sharedOverlay)
        val second = StoriesModel.Slide(StoriesModel.Slide.Background.Url("same"), sharedOverlay)
        val third = slide("last")
        val model = StoriesModel(listOf(first, second, third))

        assertEquals(2, model.getNextIndex(1))
        assertEquals(1, model.getPreviousIndex(2))
        assertEquals(third, model.getNext(second))
    }

    @Test
    fun slideNavigation_usesIdentityForEqualSlides() {
        val sharedOverlay: @Composable () -> Unit = {}
        val first = StoriesModel.Slide(StoriesModel.Slide.Background.Url("same"), sharedOverlay)
        val second = StoriesModel.Slide(StoriesModel.Slide.Background.Url("same"), sharedOverlay)
        val model = StoriesModel(listOf(first, second, slide("last")))

        assertEquals(slide("last").background, model.getNext(second)?.background)
    }

    @Test
    fun unknownSlide_doesNotWrapToFirstSlide() {
        val model = StoriesModel(listOf(slide("1"), slide("2")))

        assertNull(model.getNext(slide("not-in-model")))
        assertNull(model.getPrevious(slide("not-in-model")))
    }

    @Test
    fun emptySlides_areRejected() {
        assertThrows(IllegalArgumentException::class.java) {
            StoriesModel(emptyList())
        }
    }

    @Test
    fun invalidInitialIndex_isRejected() {
        val slides = listOf(slide("1"))

        assertThrows(IllegalArgumentException::class.java) {
            StoriesModel(slides = slides, initialSlideIndex = -1)
        }
        assertThrows(IllegalArgumentException::class.java) {
            StoriesModel(slides = slides, initialSlideIndex = 1)
        }
    }

    @Test
    fun nonPositiveSlideDuration_isRejected() {
        val slides = listOf(slide("1"))

        assertThrows(IllegalArgumentException::class.java) {
            StoriesModel(slides, config = StoriesDefaults(slideDurationMs = 0L))
        }
        assertThrows(IllegalArgumentException::class.java) {
            StoriesModel(slides, config = StoriesDefaults(slideDurationMs = -1L))
        }
    }

    @Test
    fun controllerCallbacks_canBeCleared() {
        val controller = StoriesController()
        var calls = 0
        controller.next = { calls++ }
        controller.previous = { calls++ }
        controller.play = { calls++ }
        controller.pause = { calls++ }

        controller.clearCallbacks()
        controller.goNext()
        controller.goPrevious()
        controller.doPlay()
        controller.doPause()

        assertEquals(0, calls)
    }
}
