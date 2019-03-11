/*
 * Copyright (C) 2017 Luke Klinker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.klinker.android.floating_tutorial

import android.content.Intent
import android.graphics.Color
import androidx.annotation.*
import androidx.cardview.widget.CardView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import xyz.klinker.android.floating_tutorial.util.DensityConverter
import xyz.klinker.android.floating_tutorial.view.ProgressIndicatorView

abstract class TutorialPage(private val activity: FloatingTutorialActivity) : FrameLayout(activity) {

    /**
     * Implement this function to provide any page configuration.
     */
    abstract fun initPage()

    private val rootLayout: CardView = activity.layoutInflater.inflate(R.layout.tutorial_page_root, null) as CardView
    private val pageContent: FrameLayout by lazy { rootLayout.findViewById<View>(R.id.tutorial_page_content) as FrameLayout }
    private val progressHolder: LinearLayout by lazy { rootLayout.findViewById<View>(R.id.tutorial_progress) as LinearLayout }
    private val nextButton: Button by lazy { rootLayout.findViewById<View>(R.id.tutorial_next_button) as Button }

    private var data: Any? = null
    private var pageIndex: Int? = null

    internal fun init(pageIndex: Int) {
        val pageCount = activity.getPageCount()
        this.pageIndex = pageIndex

        val layoutParams = FrameLayout.LayoutParams(DensityConverter.toDp(activity, 316), ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.CENTER

        rootLayout.layoutParams = layoutParams
        super.addView(rootLayout)

        if (pageIndex == pageCount - 1) setNextButtonText(R.string.tutorial_finish)
        nextButton.setOnClickListener { activity.onNextPressed() }

        initPage()

        if (pageCount > 0) {
            post {
                for (i in 0 until pageCount) {
                    val indicatorParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    indicatorParams.leftMargin = DensityConverter.toDp(context, 8)

                    val progressIndicator = ProgressIndicatorView(activity)
                    progressIndicator.layoutParams = indicatorParams

                    if (i == pageIndex) progressIndicator.current = true

                    progressHolder.addView(progressIndicator)
                }
            }
        }
    }

    /**
     * Called whenever the [TutorialPage] is displayed.
     *
     * Override this if you want to perform any action whenever the page is shown. For animation work,
     * you should instead override [animateLayout].
     *
     * @param firstTimeShown whether or not this page has been shown previously.
     */
    @CallSuper
    open fun onShown(firstTimeShown: Boolean) {
        if (firstTimeShown) {
            animateLayout()
        }
    }

    /**
     * Called the first time the [TutorialPage] is displayed. Override this to do any animation work
     * on the page elements.
     */
    open fun animateLayout() { }

    /**
     * Get the [FloatingTutorialActivity] that this [TutorialPage] is attached to.
     */
    fun getActivity(): FloatingTutorialActivity {
        return activity
    }

    /**
     * Set the content of the [TutorialPage]. This should be called in your [initPage] function.
     *
     * @param layout the layout resource you want to use.
     */
    fun setContentView(@LayoutRes layout: Int) {
        setContentView(activity.layoutInflater.inflate(layout, this, false))
    }

    /**
     * Set the content of the [TutorialPage]. This should be called in your [initPage] function.
     *
     * @param content the [View] that the page will display.
     */
    fun setContentView(content: View) {
        pageContent.addView(content)
    }

    /**
     * Set the result of the [FloatingTutorialActivity].
     *
     * @param result the result that will be passed to calling Activity.
     */
    fun setActivityResult(result: Int) {
        activity.setResult(result)
    }

    /**
     * Set the result of the [FloatingTutorialActivity].
     *
     * @param result the result that will be passed to calling Activity.
     */
    fun setActivityResult(result: Int, data: Intent) {
        activity.setResult(result, data)
    }

    /**
     * Set the data for this page so that the next page can retrieve the result
     *
     * @param data the data associated with this page, retrievable with the [TutorialPage.getPreviousPageResult]
     */
    fun setPageResultData(data: Any?) {
        this.data = data
    }

    /**
     * Get the data that is set as the result for this page.
     */
    fun getPageResultData(): Any? = data

    /**
     * Get the data that is set as the result for the previous page.
     */
    fun getPreviousPageResult(): Any? {
        if (pageIndex == 0) throw IllegalStateException("Cannot get the previous page result on the first page of the tutorial")
        return activity.provider.tutorialPages[pageIndex!! - 1].getPageResultData()
    }

    /**
     * Hide the "Next" button to handle advancing the paging through a [View] you define in the page.
     */
    fun hideNextButton() {
        nextButton.visibility = View.INVISIBLE
    }

    /**
     * Set the color of the "Next" button's text. The default is close to black.
     *
     * @param newColor the color resource you want to use.
     */
    fun setNextButtonTextColorResource(@ColorRes newColor: Int) {
        setNextButtonTextColor(activity.resources.getColor(newColor))
    }

    /**
     * Set the color of the "Next" button's text. The default is close to black.
     *
     * @param newColor the color value you want to use.
     */
    fun setNextButtonTextColor(newColor: Int) {
        nextButton.setTextColor(newColor)
    }

    /**
     * Set the text for the "Next" button. The default is simply "Next".
     *
     * @param text the string resource you want to use.
     */
    fun setNextButtonText(@StringRes text: Int) {
        setNextButtonText(activity.getString(text))
    }

    /**
     * Set the text for the "Next" button. The default is simply "Next".
     *
     * @param text the string you want to use.
     */
    fun setNextButtonText(text: String) {
        nextButton.text = text
    }

    /**
     * Set the color of the progress indicator bubbles. The default is close to black.
     * Usually, this would match the color you set with [setNextButtonTextColor].
     *
     * @param newColor the color resource you want to use.
     */
    fun setProgressIndicatorColorResource(@ColorRes newColor: Int) {
        setProgressIndicatorColor(activity.resources.getColor(newColor))
    }

    /**
     * Set the color of the progress indicator bubbles. The default is close to black.
     * Usually, this would match the color you set with [setNextButtonTextColor].
     *
     * @param newColor the color value you want to use.
     */
    fun setProgressIndicatorColor(newColor: Int) {
        progressHolder.post {
            (0 until progressHolder.childCount)
                    .map { progressHolder.getChildAt(it) as ProgressIndicatorView }
                    .forEach { it.setColor(newColor) }
        }
    }

    /**
     * Set the background color for the [TutorialPage]. The default is close to white.
     *
     * @param newColor the color resource you want to use.
     */
    fun setBackgroundColorResource(@ColorRes newColor: Int) {
        setBackgroundColor(activity.resources.getColor(newColor))
    }

    /**
     * Set the background color for the [TutorialPage]. The default is close to white.
     *
     * @param newColor the color value you want to use.
     */
    override fun setBackgroundColor(newColor: Int) {
        rootLayout.setCardBackgroundColor(newColor)

        val color = if (isColorDark(newColor)) {
            resources.getColor(R.color.tutorial_dark_background_indicator)
        } else {
            resources.getColor(R.color.tutorial_light_background_indicator)
        }

        setProgressIndicatorColor(color)
        setNextButtonTextColor(color)
    }

    companion object {
        private val DARKNESS_THRESHOLD = 0.30
        private fun isColorDark(color: Int): Boolean {
            val darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
            return darkness >= DARKNESS_THRESHOLD
        }
    }
}