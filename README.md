![feature graphic](artwork/sample.png)

# Android Floating Tutorial Activity

This is a simple, easy-to-implement, and highly customizable framework for making a quick tutorial or showing some kind of alert within your app. I used this style of tutorial/dialog in a few places for my app [Pulse SMS](https://play.google.com/store/apps/details?id=xyz.klinker.messenger) and really enjoyed the UX that it brought. It is simply, doesn't get in the user's way, and looks nice. In Pulse, I used it for the login page, prompting the user to rate the app, and walking them through the purchase process, but there is no end to the number of use-cases for an app!

The goal of this library isn't to confine you to certain UI styles or guidelines that I have made. The goal here is to provide you with a framework that you can build off of yourself. The content of these tutorial pages can be anything you want. That said, I have provided a few examples here, of UIs that have worked well for me and that I think provide a pretty great experience.

*Note: the library itself is in [Kotlin](https://kotlinlang.org/), which is now [native to Android](https://blog.jetbrains.com/kotlin/2017/05/kotlin-on-android-now-official/). I have created sample applications in both* **Kotlin** *and* **Java**. *So, as you go through and figure out how to implement this library, you can use either sample as a reference. They are functionally equivalent.*

*If you would like to see the usage section of this README in* **Java** *please head to the [Java-flavored README](README-JAVA.md).*

## Including it in your Project

Add the following to your app's `build.gradle` file:

```java
dependencies {
    compile "com.klinkerapps:floating-tutorial:1.0.2"
}
```

## Usage

You will need to create a new `Activity` that extends `FloatingTutorialActivity` and implements the `FloatingTutorialActivity#getPages` function:

```kotlin
class ExampleActivity : FloatingTutorialActivity() {
    override fun getPages(): List<TutorialPage> {
        ...
    }
}
```

That function will return the list of pages that the user will see. The simplest implementation of providing a page would look like this:

```kotlin
override fun getPages() = listOf(
    object : TutorialPage(this@SimpleDialogExample) {
        override fun initPage() {
            setContentView(R.layout.simple_layout)
        }
    })
)
```

A `TutorialPage` is similar to an `Activity` or `Fragment`. After you have used the `setContentView` function, you can manipulate those `Views` with the `findViewById` method. The tutorial pages do not enforce any UI standards for the content, so you can design these layouts to be whatever you want. Each page will automatically get the "Next" button and the progress indicators added, unless you specifically remove them.

To customize the look of the page, there are numerous options available:

```kotlin
object : TutorialPage(this@SimpleDialogExample) {
    override fun initPage() {
        // set the layout for the individual page
        setContentView(R.layout.simple_layout)

        // manipulate views however you want
        val view = findViewById<View>(R.id.example_view)
        view.setBackgroundColor(Color.RED)
        view.setOnClickListener { }

        // Customize the look of the page
        hideNextButton()                          // useful if you want to handle going to the next page, within your layout, instead of with this button
        setNextButtonColor(Color.WHITE)           // you can also provide a color resource value with the setNextButtonColorResource function
        setNextButtonText("New Text")             // you can also provide a string resource value
        setProgressIndicatorColor(Color.WHITE)    // you can also provide a color resource value with the setProgressIndicatorColorResource function
        setBackgroundColor(Color.BLACK)           // the default background color is white. Changing it here will automatically adjust the progress indicator and next button colors, based on whether or not the background is light or dark.
    }
})
```

Any other customization you want can come directly from your layout file. My examples show the primary color for the background of the top text. You could do that inside of your layout, if you like that look.

### Animating the layouts

<p align="center">
  <img src="artwork/animation.gif">
</p>

In my examples, as well as my own usage of this library, I like to provide subtle animations the first time that a user views a `TutorialPage`. If they were to go backwards in the tutorial, then return to a page for a second time, I do not show the animation again. If you would like to animate your pages in this way, you can override the `TutorialPage#animateLayout` function.

```kotlin
object : TutorialPage(this@SimpleDialogExample) {
    ...

    override fun animateLayout() {
        val view = findViewById<View>(R.id.example_view)

        // do some animation here. I have an example animation that you could use:
        // https://github.com/klinker24/Android-FloatingTutorialActivity/blob/master/sample-kotlin/src/main/java/xyz/klinker/floating_tutorial/util/AnimationHelper.kt
        AnimationHelper.quickViewReveal(view, 300)
    }
})
```

If you would rather animate the `View` every time the page is shown, you could override the `TutorialPage#onShown(firstTimeShown: Boolean)` method, instead. If you override that, be sure to call the super class method.

### Providing results from the individual pages, or the entire tutorial

Sometimes, you may need to have some state in your tutorial. If you are using the tutorial to log in a user, for example, you will probably need the calling `Activity` to know if the login was successful or not.

For an example of how to provide an `Activity` result from your `TutorialPage`, please see the [SelectionDialogExample](sample-kotlin/src/main/java/xyz/klinker/floating_tutorial/examples/SelectionDialogExample.kt).

Other times, you may need to know the result of the previous page, to display the UI for the next page. In the [RateItExample](sample-kotlin/src/main/java/xyz/klinker/floating_tutorial/examples/RateItExample.kt), the first page asks the user to give a thumbs up or thumbs down.

* If the user selects thumbs down, the second page will ask them if they want to provide feedback.
* If the user selects thumbs up, the second page will ask them if they want to rate the app on the Play Store.

This is a good example of the need to communicate the previous page's result to the current page, and customizing the current page, based on that result.

## Examples Explained

The sample app comes with a few different examples, highlighting different functionality:

* [SimpleDialogExample](sample-kotlin/src/main/java/xyz/klinker/floating_tutorial/examples/SimpleDialogExample.kt): a quick and nice looking replacement for an alert dialog, if you want it. This demonstrates a single page and some of the animation capabilites.
* [SelectionDialogExample](sample-kotlin/src/main/java/xyz/klinker/floating_tutorial/examples/SelectionDialogExample.kt): this demonstrates a selection process. It will provide the selection result as the `Activity` result.
* [FeatureWalkthroughExample](sample-kotlin/src/main/java/xyz/klinker/floating_tutorial/examples/FeatureWalkthroughExample.kt): this is a simple feature tutorial that could be used anywhere in your apps. It also demonstrates changing the background color and providing multiple pages.
* [RateItExample](sample-kotlin/src/main/java/xyz/klinker/floating_tutorial/examples/RateItExample.kt): as discussed above, this example demonstrates passing data between the current and the previous page and manipulating the page, based on that data.
* [PulseSmsPurchaseExample](sample-kotlin/src/main/java/xyz/klinker/floating_tutorial/examples/PulseSmsPurchaseExample.kt): this demonstrates one way that I have used the `floating-tutorial` in [Pulse SMS](https://play.google.com/store/apps/details?id=xyz.klinker.messenger). It demonstrates more complex layouts and animations, as well as an `Activity` result.

## Contributing

Please fork this repository and contribute back using [pull requests](https://github.com/klinker24/Android-FloatingTutorialActivity/pulls). Features can be requested using [issues](https://github.com/klinker24/Android-FloatingTutorialActivity/issues). All code, comments, and critiques are greatly appreciated.

## Changelog

The full changelog for the library can be found [here](https://github.com/klinker24/Android-FloatingTutorialActivity/blob/master/CHANGELOG.md).

## License

    Copyright 2017 Luke Klinker

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
