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

internal class TutorialPageProvider(private val activity: FloatingTutorialActivity) {

    var currentPageNumber = 0

    private val displayedPages = hashSetOf(0)
    val tutorialPages: List<TutorialPage> by lazy { activity.getPages() }

    init {
        if (tutorialPages.isEmpty()) throw IllegalStateException("No pages have been configured.")
    }

    /**
     * Get the currently displayed page.
     */
    fun currentPage(): TutorialPage = tutorialPages[currentPageNumber]

    /**
     * Get the next page, or null, if the user is on the last page.
     */
    fun nextPage(): TutorialPage? =
            if (currentPageNumber + 1 < tutorialPages.size) tutorialPages[currentPageNumber + 1] else null

    /**
     * Get the previous page, or null, if the user is on the first page.
     */
    fun previousPage(): TutorialPage? =
            if (currentPageNumber > 0) tutorialPages[currentPageNumber - 1] else null

    /**
     * Decrement the current page.
     *
     * @throws IllegalStateException if the current page is the first page.
     */
    fun decrementPage() =
            if (currentPageNumber == 0) {
                throw IllegalStateException("current page should never be less than zero")
            } else {
                currentPageNumber--
            }

    /**
     * Increment the current page.
     *
     * @throws IllegalStateException if the current page is the last page.
     */
    fun incrementPage() =
            if (currentPageNumber + 1 == tutorialPages.size) {
                throw IllegalStateException("current page cannot be more than the size of the tutorial page list")
            } else {
                currentPageNumber++
                displayedPages.add(currentPageNumber)
            }

    /**
     * Whether or not the [FloatingTutorialActivity] has visited this page in the past.
     *
     * @param page the page number.
     * @return true if this is the first time the [FloatingTutorialActivity] has visited this page. False otherwise.
     */
    fun isFirstVisitToPage(page: Int) = !displayedPages.contains(page)
}