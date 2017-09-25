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

package xyz.klinker.floating_tutorial.examples

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.TextView

import java.util.ArrayList

import xyz.klinker.android.floating_tutorial.FloatingTutorialActivity
import xyz.klinker.android.floating_tutorial.TutorialPage
import xyz.klinker.floating_tutorial.R
import xyz.klinker.floating_tutorial.util.AnimationHelper

class RateItExample : FloatingTutorialActivity() {

    override fun getPages() = listOf(PageOne(this), PageTwo(this))

    @SuppressLint("ViewConstructor")
    private class PageOne(activity: FloatingTutorialActivity) : TutorialPage(activity) {
        override fun initPage() {
            setContentView(R.layout.example_rate_it_page_1)
            hideNextButton()

            findViewById<View>(R.id.thumbs_up).setOnClickListener {
                setPageResultData(RateItResult.THUMBS_UP)
                getActivity().onNextPressed()
            }

            findViewById<View>(R.id.thumbs_down).setOnClickListener {
                setPageResultData(RateItResult.THUMBS_DOWN)
                getActivity().onNextPressed()
            }
        }

        override fun onShown(firstTimeShown: Boolean) {
            super.onShown(firstTimeShown)
            setActivityResult(Activity.RESULT_CANCELED)
        }

        override fun animateLayout() {
            AnimationHelper.animateGroup(
                    findViewById(R.id.bottom_text),
                    findViewById(R.id.thumbs_up),
                    findViewById(R.id.thumbs_down)
            )
        }
    }

    @SuppressLint("ViewConstructor")
    private class PageTwo(activity: FloatingTutorialActivity) : TutorialPage(activity) {
        override fun initPage() {
            setContentView(R.layout.example_rate_it_page_2)
        }

        override fun onShown(firstTimeShown: Boolean) {
            super.onShown(firstTimeShown)

            val titleText = findViewById<View>(R.id.top_text) as TextView
            val summaryText = findViewById<View>(R.id.bottom_text) as TextView

            val previousResult = getPreviousPageResult() as RateItResult?
            if (previousResult == RateItResult.THUMBS_UP) {
                titleText.setText(R.string.rate_it_page_2_good_title)
                summaryText.setText(R.string.rate_it_page_2_good_summary)

                val data = Intent()
                data.putExtra(RESULT_DATA_TEXT, "Send that user to the Play Store to give you a rating!")
                setActivityResult(Activity.RESULT_OK, data)

                setNextButtonText(R.string.rate_it)
            } else {
                titleText.setText(R.string.rate_it_page_2_bad_title)
                summaryText.setText(R.string.rate_it_page_2_bad_summary)

                val data = Intent()
                data.putExtra(RESULT_DATA_TEXT, "They have feedback for your app. Maybe allow them to send you an email?")
                setActivityResult(Activity.RESULT_OK, data)

                setNextButtonText(R.string.send_email)
            }
        }

        override fun animateLayout() {
            AnimationHelper.quickViewReveal(findViewById(R.id.bottom_text), 300)
        }
    }

    private enum class RateItResult {
        THUMBS_UP, THUMBS_DOWN
    }

    companion object {
        val RESULT_DATA_TEXT = "result_data_text"
    }
}