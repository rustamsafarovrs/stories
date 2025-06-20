package tj.rustamsafarovrs.stories

/**
 * Created by Rustam Safarov on 6/20/25.
 * github.com/rustamsafarovrs
 */

class StoriesController {

    internal var next: (() -> Unit)? = null
    internal var previous: (() -> Unit)? = null
    internal var play: (() -> Unit)? = null
    internal var pause: (() -> Unit)? = null

    fun goNext() {
        next?.invoke()
    }

    fun goPrevious() {
        previous?.invoke()
    }

    fun doPlay() {
        play?.invoke()
    }

    fun doPause() {
        pause?.invoke()
    }
}