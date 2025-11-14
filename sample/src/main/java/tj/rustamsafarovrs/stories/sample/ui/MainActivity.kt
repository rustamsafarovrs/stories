package tj.rustamsafarovrs.stories.sample.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tj.rustamsafarovrs.stories.R
import tj.rustamsafarovrs.stories.Stories
import tj.rustamsafarovrs.stories.StoriesController
import tj.rustamsafarovrs.stories.StoriesModel
import tj.rustamsafarovrs.stories.drawableBackground
import tj.rustamsafarovrs.stories.sample.ui.theme.StoriesTheme
import tj.rustamsafarovrs.stories.sample.ui.theme.Typography
import tj.rustamsafarovrs.stories.urlBackground

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StoriesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    ContentUI()
                }
            }
        }
    }
}

@Composable
private fun ContentUI() {
    var showingStories by remember { mutableStateOf(false) }
    val controller = remember { StoriesController() }

    Box(Modifier.fillMaxSize()) {
        if (showingStories) {
            Stories(
                model = StoriesModel(sampleList(controller)),
                controller = controller,
                didFinish = { showingStories = false }
            )
        } else {
            Button(
                onClick = { showingStories = true },
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(
                    "Start stories",
                    Modifier.padding(horizontal = 20.dp, vertical = 4.dp),
                    style = Typography.labelLarge
                )
            }
        }
    }
}

@Composable
private fun SlideOverlay(controller: StoriesController) {
    Column(
        Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(top = 60.dp, bottom = 40.dp)
            .padding(horizontal = 20.dp)
    ) {
        Text(
            "Lorem ipsum dolor sit amet",
            color = Color.White,
            style = Typography.titleLarge.copy(
                fontSize = 42.sp,
                lineHeight = 50.sp
            )
        )
        Text(
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc purus ipsum, tincidunt in libero vitae, fringilla volutpat eros. In ornare, nisl in ultricies aliquet, nunc ipsum pharetra augue, pellentesque ullamcorper purus lorem quis ligula. Mauris laoreet arcu massa, at iaculis metus aliquam congue. ",
            color = Color.LightGray,
            style = Typography.bodyLarge,
            modifier = Modifier.padding(top = 18.dp)
        )
        Spacer(Modifier.weight(1f))
        Button(onClick = { controller.goPrevious() }) {
            Text(
                "Go to previous",
                color = Color.White,
                style = Typography.titleMedium,
                modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 20.dp)
            )
        }
        Spacer(Modifier.height(20.dp))
        Button(onClick = { controller.goNext() }) {
            Text(
                "Action Button",
                color = Color.White,
                style = Typography.titleMedium,
                modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 20.dp)
            )
        }
    }
}

private fun sampleList(controller: StoriesController): List<StoriesModel.Slide> {
    return listOf(
        StoriesModel.Slide(drawableBackground(R.drawable.sample_slide_0)),
        StoriesModel.Slide(urlBackground("https://raw.githubusercontent.com/rustamsafarovrs/adibon.tj/refs/heads/master/img/pexels-mostafa-ft-shots-98391-9882401.jpg")),
        StoriesModel.Slide(drawableBackground(R.drawable.sample_slide_1)),
        StoriesModel.Slide(drawableBackground(R.drawable.sample_slide_2), { SlideOverlay(controller) })
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StoriesTheme {
        ContentUI()
    }
}

@Preview(showBackground = true)
@Composable
fun StoriesPreview() {
    StoriesTheme {
        Stories(
            model = StoriesModel(
                listOf(
                    StoriesModel.Slide(drawableBackground(R.drawable.sample_slide_0)),
                    StoriesModel.Slide(drawableBackground(R.drawable.sample_slide_1)),
                ),
            )
        )
    }
}