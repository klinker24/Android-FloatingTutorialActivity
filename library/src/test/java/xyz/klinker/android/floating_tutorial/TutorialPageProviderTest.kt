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

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import xyz.klinker.android.floating_tutorial.suite.FloatingTutorialJunitSuite

class TutorialPageProviderTest : FloatingTutorialJunitSuite() {

    private var provider: TutorialPageProvider? = null

    @Mock
    var activity: FloatingTutorialActivity? = null

    @Mock var pageOne: TutorialPage? = null
    @Mock var pageTwo: TutorialPage? = null
    @Mock var pageThree: TutorialPage? = null

    @Before
    fun setUp() {
        `when`(activity!!.getPages()).thenReturn(listOf(
                pageOne!!, pageTwo!!, pageThree!!
        ))

        provider = TutorialPageProvider(activity!!)
    }

    @Test(expected = IllegalStateException::class)
    fun shouldEnsurePagesExist() {
        `when`(activity!!.getPages()).thenReturn(emptyList())
        provider = TutorialPageProvider(activity!!)
    }

    @Test
    fun shouldIncrementPage() {
        provider!!.currentPageNumber = 0
        provider!!.incrementPage()

        assertEquals(pageTwo!!, provider!!.currentPage())
        assertFalse(provider!!.isFirstVisitToPage(1))
    }

    @Test(expected = IllegalStateException::class)
    fun shouldPreventIncrementingToInvalidPage() {
        provider!!.currentPageNumber = 2
        provider!!.incrementPage()
    }

    @Test
    fun shouldDecrementPage() {
        provider!!.currentPageNumber = 1
        provider!!.decrementPage()

        assertEquals(pageOne!!, provider!!.currentPage())
    }

    @Test(expected = IllegalStateException::class)
    fun shouldPreventDecrementingToInvalidPage() {
        provider!!.currentPageNumber = 0
        provider!!.decrementPage()
    }

    @Test
    fun shouldStartOnCurrentPage() {
        assertEquals(pageOne!!, provider!!.currentPage())
    }

    @Test
    fun shouldGetNextPage() {
        assertEquals(pageTwo!!, provider!!.nextPage())
    }

    @Test
    fun shouldGetPreviousPage() {
        provider!!.currentPageNumber = 1
        assertEquals(pageOne!!, provider!!.previousPage())
    }

    @Test
    fun canProvideNullNextPage() {
        provider!!.currentPageNumber = 2
        assertNull(provider!!.nextPage())
    }

    @Test
    fun canProvideNullPreviousPage() {
        provider!!.currentPageNumber = 0
        assertNull(provider!!.previousPage())
    }

    @Test
    fun visitedPagesStartsWithFirstPage() {
        assertFalse(provider!!.isFirstVisitToPage(0))
        assertTrue(provider!!.isFirstVisitToPage(1))
    }
}