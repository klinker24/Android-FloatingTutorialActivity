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

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.VisibleForTesting
import android.view.View
import android.view.ViewAnimationUtils

@SuppressLint("VisibleForTests")
internal class TutorialPresenter(private val activity: FloatingTutorialActivity,
                        private val pageProvider: TutorialPageProvider) {

    /**
     * Start the [FloatingTutorialActivity] by doing a circular reveal and loading the first page.
     */
    fun start() {
        revealIn()
        pageProvider.currentPage().onShown(true)
    }

    /**
     * Called when the user touches the "back" button on their device.
     */
    fun onBackPressed() {
        val previousPage = pageProvider.previousPage()
        if (previousPage == null) {
            revealOut()
        } else {
            previousPage.onShown(false)

            slideOut(pageProvider.currentPage(), previousPage)
            pageProvider.decrementPage()
        }
    }

    /**
     * Called when the user touches the "Next" button on the [TutorialPage], or the
     * [FloatingTutorialActivity] implementor manually calls it.
     */
    fun onNextPressed() {
        val nextPage = pageProvider.nextPage()
        if (nextPage == null) {
            revealOut()

            if (activity is TutorialFinishedListener) {
                activity.onTutorialFinished()
            }
        } else {
            nextPage.onShown(pageProvider.isFirstVisitToPage(pageProvider.currentPageNumber + 1))

            slideIn(pageProvider.currentPage(), nextPage)
            pageProvider.incrementPage()
        }
    }

    /**
     * Animate the activity in. If the user's device is Lollipop or above ([Build.VERSION_CODES.LOLLIPOP]),
     * it will be a circular reveal animation. Otherwise, it will be a alpha transition.
     */
    @VisibleForTesting
    internal fun revealIn() {
        val firstPage = pageProvider.currentPage()
        firstPage.visibility = View.VISIBLE

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && pageProvider.currentPage() !is BottomSheetTutorialPage) {
            val cx = firstPage.width / 2
            val cy = firstPage.height / 2
            val finalRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()

            try {
                ViewAnimationUtils.createCircularReveal(firstPage, cx, cy, 0f, finalRadius).start()
            } catch (e: IllegalStateException) {
                firstPage.alpha = 0f
                firstPage.animate().alpha(1f).start()
            }
        } else {
            firstPage.alpha = 0f
            firstPage.animate().alpha(1f).setDuration(200).start()
        }

    }

    /**
     * Animate the activity out. If the user's device is Lollipop or above ([Build.VERSION_CODES.LOLLIPOP]),
     * it will be a circular reveal animation. Otherwise, it will be a alpha transition.
     */
    @VisibleForTesting
    internal fun revealOut() {
        val currentPage = pageProvider.currentPage()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && pageProvider.currentPage() !is BottomSheetTutorialPage) {
            val cx = currentPage.width / 2
            val cy = currentPage.height / 2
            val initialRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()

            try {
                val anim = ViewAnimationUtils.createCircularReveal(currentPage, cx, cy, initialRadius, 0f)
                anim.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        currentPage.visibility = View.INVISIBLE
                        activity.close()
                    }
                })

                anim.start()
            } catch (e: IllegalStateException) {
                currentPage.animate().alpha(0f).setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        activity.close()
                    }
                }).start()
            }
        } else {
            currentPage.animate().alpha(0f).setDuration(200).setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    activity.close()
                }
            }).start()
        }
    }

    /**
     * Slide the next [TutorialPage] into view to replace the previous page.
     */
    @VisibleForTesting
    internal fun slideIn(currentPage: TutorialPage, nextPage: TutorialPage) {
        nextPage.visibility = View.VISIBLE
        nextPage.alpha = 0f
        nextPage.translationX = nextPage.width.toFloat()
        nextPage.animate()
                .alpha(1f)
                .translationX(0f)
                .setListener(null)
                .start()

        currentPage.animate()
                .alpha(0f)
                .translationX((-1 * currentPage.width).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        currentPage.visibility = View.INVISIBLE
                        currentPage.translationX = 0f
                    }
                }).start()
    }

    /**
     * Slide the previous [TutorialPage] into view, to replace the current page.
     */
    @VisibleForTesting
    internal fun slideOut(currentPage: TutorialPage, previousPage: TutorialPage) {
        previousPage.visibility = View.VISIBLE
        previousPage.alpha = 0f
        previousPage.translationX = (-1 * previousPage.width).toFloat()
        previousPage.animate()
                .alpha(1f)
                .translationX(0f)
                .setListener(null)
                .start()

        currentPage.animate()
                .alpha(0f)
                .translationX(currentPage.width.toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        currentPage.visibility = View.INVISIBLE
                        currentPage.translationX = 0f
                    }
                }).start()
    }
}