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

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.FrameLayout

abstract class FloatingTutorialActivity : AppCompatActivity() {

    /**
     * Implement to provide the page for the [FloatingTutorialActivity].
     */
    abstract fun getPages(): List<TutorialPage>

    internal val provider: TutorialPageProvider by lazy { TutorialPageProvider(this) }
    private val presenter: TutorialPresenter by lazy { TutorialPresenter(this, provider) }

    private val pageHolder: FrameLayout by lazy { findViewById<View>(R.id.tutorial_page_holder) as FrameLayout }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.tutorial_activity_base)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.BLACK
        }

        provider.tutorialPages.forEach { addPage(it) }
        Handler().postDelayed({ presenter.start() }, 100)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    /**
     * The [TutorialPage] calls this automatically when the user touches the "Next" button.
     * If you have hidden the "Next" button, you will be responsible for calling
     * this on your [TutorialPage].
     */
    fun onNextPressed() {
        presenter.onNextPressed()
    }

    /**
     * Close the activity.
     *
     * If you have hidden the "Next" button on the [TutorialPage], you may want to consider calling
     * this instead of just [finish] on the last [TutorialPage].
     */
    fun finishAnimated() {
        presenter.revealOut()
    }

    /**
     * Finishes the [FloatingTutorialActivity] with no activity animation, after the [View] has
     * been animated closed.
     */
    internal fun close() {
        finish()
        overridePendingTransition(0, 0)
    }

    /**
     * The number of pages that were provided to the [FloatingTutorialActivity].
     */
    internal fun getPageCount(): Int = provider.tutorialPages.size

    private fun addPage(page: TutorialPage) {
        page.visibility = View.INVISIBLE
        page.init(provider.tutorialPages.indexOf(page))

        pageHolder.addView(page)
    }
}