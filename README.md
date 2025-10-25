# Android Stories Library

[![Latest Version](https://img.shields.io/badge/version-1.1.0-blue.svg)](https://github.com/rustamsafarovrs/stories/releases)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](LICENSE)
[![Android](https://img.shields.io/badge/platform-Android-green.svg)](https://developer.android.com/)
[![Compose](https://img.shields.io/badge/Jetpack%20Compose-2025.06.00-blue.svg)](https://developer.android.com/jetpack/compose)

A modern Android library for creating Instagram/Snapchat-style stories using Jetpack Compose. This library provides a customizable and easy-to-use component for displaying sequential content with automatic progression and user interaction controls.

## Features

- 🎬 **Automatic Story Progression**: Stories automatically advance with customizable timing
- 🎨 **Multiple Background Types**: Support for drawable resources and URLs
- 🎮 **Interactive Controls**: Tap to navigate, pause/play functionality
- 📱 **Customizable UI**: Configurable progress bars, colors, and animations
- 🎯 **Compose Integration**: Built with Jetpack Compose for modern Android development
- 📦 **Lightweight**: Minimal dependencies and optimized performance

## Installation

### Gradle

Add the dependency to your app's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.rustamsafarovrs:stories:1.1.0")
}
```

## Quick Start

### Basic Usage

```kotlin
@Composable
fun MyStories() {
    val storiesModel = StoriesModel(
        slides = listOf(
            StoriesModel.Slide(drawableBackground(R.drawable.image1)),
            StoriesModel.Slide(urlBackground("https://example.com/image.jpg")),
            StoriesModel.Slide(drawableBackground(R.drawable.image2))
        )
    )
    
    Stories(
        model = storiesModel,
        didFinish = { 
            // Handle story completion
        }
    )
}
```

### With Custom Overlay

```kotlin
@Composable
fun StoriesWithOverlay() {
    val controller = remember { StoriesController() }
    
    val storiesModel = StoriesModel(
        slides = listOf(
            StoriesModel.Slide(
                background = drawableBackground(R.drawable.image1),
                overlay = { 
                    Text(
                        "Custom overlay content",
                        color = Color.White,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            )
        )
    )
    
    Stories(
        model = storiesModel,
        controller = controller,
        didFinish = { /* Handle completion */ }
    )
}
```

## API Reference

### Stories Composable

The main composable for displaying stories.

```kotlin
@Composable
fun Stories(
    model: StoriesModel,
    controller: StoriesController = remember { StoriesController() },
    didFinish: () -> Unit = {}
)
```

**Parameters:**
- `model`: The stories data model containing slides and configuration
- `controller`: Optional controller for programmatic navigation
- `didFinish`: Callback invoked when all stories are completed

### StoriesModel

Data class representing the stories content and configuration.

```kotlin
data class StoriesModel(
    val slides: List<Slide>,
    val config: StoriesDefaults = StoriesDefaults()
)
```

### Slide

Individual story slide with background and optional overlay.

```kotlin
data class Slide(
    val background: Background,
    val overlay: @Composable () -> Unit = {}
)
```

### Background Types

#### Drawable Background
```kotlin
StoriesModel.Slide(drawableBackground(R.drawable.my_image))
```

#### URL Background
```kotlin
StoriesModel.Slide(urlBackground("https://example.com/image.jpg"))
```


### StoriesController

Control story playback programmatically.

```kotlin
val controller = remember { StoriesController() }

// Navigate to next slide
controller.goNext()

// Navigate to previous slide
controller.goPrevious()

// Pause story progression
controller.doPause()

// Resume story progression
controller.doPlay()
```

### StoriesDefaults

Configuration options for story behavior and appearance.

```kotlin
data class StoriesDefaults(
    val slideDurationMs: Long = 3_000L,                    // Duration per slide
    val progressSpaceBetween: Dp = 8.dp,                  // Space between progress bars
    val progressBackgroundColor: Color = Color.LightGray,  // Progress bar background
    val progressColor: Color = Color.Blue,                 // Progress bar color
    val progressHeight: Dp = 4.dp,                         // Progress bar height
    val progressRadius: Dp = 2.dp,                        // Progress bar corner radius
    val clickToChangeEnabled: Boolean = true,             // Enable tap navigation
    val clickToPauseEnabled: Boolean = true,              // Enable tap to pause
    val shimmerBackgroundColor: Color = Color.White,       // Shimmer effect background
    val shimmerHighlightColor: Color = Color.LightGray,   // Shimmer effect highlight
    val shimmerDuration: Int = 1000                       // Shimmer animation duration
)
```

## User Interactions

### Tap Navigation
- **Left side tap**: Go to previous slide
- **Right side tap**: Go to next slide
- **Tap and hold**: Pause story progression

### Gesture Controls
- Tap and hold anywhere to pause
- Release to resume playback
- Tap left/right edges to navigate

## Customization Examples

### Custom Configuration

```kotlin
val customConfig = StoriesDefaults(
    slideDurationMs = 5_000L,
    progressColor = Color.Red,
    progressHeight = 6.dp,
    clickToPauseEnabled = false
)

val storiesModel = StoriesModel(
    slides = slides,
    config = customConfig
)
```

### Custom Overlay with Actions

```kotlin
@Composable
fun CustomSlideOverlay(controller: StoriesController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Story Title",
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { controller.goPrevious() }) {
                Text("Previous")
            }
            Button(onClick = { controller.goNext() }) {
                Text("Next")
            }
        }
    }
}
```

## Requirements

- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 35
- **Kotlin**: 2.1.21+
- **Jetpack Compose**: 2025.06.00+
- **Java**: 17+

## Dependencies

The library uses the following dependencies:
- Jetpack Compose UI
- Jetpack Compose Material3
- Glide Compose (for image loading)
- Landscapist Glide (for image display with shimmer effects)

## Sample App

The project includes a sample app demonstrating various usage patterns. Run the sample to see the library in action.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the LICENSE file for details.
