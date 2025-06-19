package tj.rustamsafarovrs.stories

/**
 * Created by Rustam Safarov on 6/19/25.
 * github.com/rustamsafarovrs
 */

sealed class SlideState {

    data object Load : SlideState()

    data object Pause : SlideState()

    data object Play : SlideState()
}