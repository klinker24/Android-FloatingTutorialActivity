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

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import xyz.klinker.android.floating_tutorial.suite.FloatingTutorialJunitSuite

class TutorialPresenterTest : FloatingTutorialJunitSuite() {

    private class FinishedListenerTutorial : FloatingTutorialActivity(), TutorialFinishedListener {
        override fun onTutorialFinished() { }
        override fun getPages(): List<TutorialPage> { return emptyList() }
    }

    private var presenter: TutorialPresenter? = null

    @Mock
    var activity: FloatingTutorialActivity? = null
    @Mock
    private var provider: TutorialPageProvider? = null

    @Before
    fun setUp() {
        presenter = spy(TutorialPresenter(activity!!, provider!!))
    }

    @Test
    fun shouldRevealOnStart() {
        val mockPage = mock(TutorialPage::class.java)

        doNothing().`when`(presenter!!).revealIn()
        doReturn(mockPage).`when`(provider!!).currentPage()

        presenter!!.start()

        verify(presenter!!).revealIn()
        verify(mockPage).onShown(true)
    }

    @Test
    fun shouldGoToPreviousPage() {
        val currentPage = mock(TutorialPage::class.java)
        val previousPage = mock(TutorialPage::class.java)
        `when`(provider!!.currentPage()).thenReturn(currentPage)
        `when`(provider!!.previousPage()).thenReturn(previousPage)

        doNothing().`when`(presenter!!).slideOut(currentPage, previousPage)
        presenter!!.onBackPressed()

        verify(provider!!).decrementPage()
        verify(presenter!!).slideOut(currentPage, previousPage)
        verify(previousPage!!).onShown(false)
    }

    @Test
    fun shouldExitWhenBackingOutOfTheFirstPage() {
        doNothing().`when`(presenter!!).revealOut()

        `when`(provider!!.previousPage()).thenReturn(null)
        presenter!!.onBackPressed()

        verify(presenter!!).revealOut()
    }

    @Test
    fun shouldGoToNextPage() {
        val currentPage = mock(TutorialPage::class.java)
        val nextPage = mock(TutorialPage::class.java)
        `when`(provider!!.currentPage()).thenReturn(currentPage)
        `when`(provider!!.nextPage()).thenReturn(nextPage)
        `when`(provider!!.isFirstVisitToPage(1)).thenReturn(true)

        doNothing().`when`(presenter!!).slideIn(currentPage, nextPage)
        presenter!!.onNextPressed()

        verify(provider!!).incrementPage()
        verify(presenter!!).slideIn(currentPage, nextPage)
        verify(nextPage!!).onShown(true)
    }

    @Test
    fun shouldGoToNextPage_whichWasPreviouslyVisited() {
        val currentPage = mock(TutorialPage::class.java)
        val nextPage = mock(TutorialPage::class.java)

        `when`(provider!!.currentPage()).thenReturn(currentPage).thenReturn(nextPage).thenReturn(currentPage)
        `when`(provider!!.nextPage()).thenReturn(nextPage)
        `when`(provider!!.previousPage()).thenReturn(currentPage)
        `when`(provider!!.isFirstVisitToPage(1)).thenReturn(true).thenReturn(false)

        doNothing().`when`(presenter!!).slideOut(nextPage, currentPage)
        doNothing().`when`(presenter!!).slideIn(currentPage, nextPage)

        presenter!!.onNextPressed()
        presenter!!.onBackPressed()
        presenter!!.onNextPressed()

        verify(nextPage!!).onShown(true)
        verify(currentPage!!).onShown(false)
        verify(nextPage).onShown(false)
    }

    @Test
    fun shouldExitWhenGoingForwardOnLastPage() {
        doNothing().`when`(presenter!!).revealOut()

        `when`(provider!!.nextPage()).thenReturn(null)
        presenter!!.onNextPressed()

        verify(presenter!!).revealOut()
    }

    @Test
    fun shouldProvideFinishedCallbackWhenAvailable() {
        val activityMock = mock(FinishedListenerTutorial::class.java)
        presenter = spy(TutorialPresenter(activityMock, provider!!))

        doNothing().`when`(presenter!!).revealOut()
        `when`(provider!!.nextPage()).thenReturn(null)
        presenter!!.onNextPressed()

        verify(activityMock).onTutorialFinished()
    }
}