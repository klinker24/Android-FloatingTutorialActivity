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

import android.app.Activity
import android.content.Intent
import android.view.View

import java.util.ArrayList

import xyz.klinker.android.floating_tutorial.FloatingTutorialActivity
import xyz.klinker.android.floating_tutorial.TutorialPage
import xyz.klinker.floating_tutorial.R
import xyz.klinker.floating_tutorial.util.AnimationHelper

class PulseSmsPurchaseExample : FloatingTutorialActivity() {

    override fun getPages() = listOf(
        object : TutorialPage(this@PulseSmsPurchaseExample) {
            override fun initPage() {
                setContentView(R.layout.example_pulse_purchase_page_1)
                setNextButtonText(R.string.try_it)
            }

            override fun animateLayout() {
                AnimationHelper.quickViewReveal(findViewById(R.id.bottom_text), 450)
                AnimationHelper.animateGroup(
                        findViewById(R.id.icon_android),
                        findViewById(R.id.icon_phone),
                        findViewById(R.id.icon_computer),
                        findViewById(R.id.icon_tablet),
                        findViewById(R.id.icon_watch)
                )
            }
        }, object : TutorialPage(this@PulseSmsPurchaseExample) {
            override fun initPage() {
                setContentView(R.layout.example_pulse_purchase_page_2)
                hideNextButton()

                findViewById<View>(R.id.monthly).setOnClickListener { finishWithResult("Monthly Subscription") }
                findViewById<View>(R.id.three_month).setOnClickListener { finishWithResult("Three Month Subscription") }
                findViewById<View>(R.id.yearly).setOnClickListener { finishWithResult("Yearly Subscription") }
                findViewById<View>(R.id.lifetime).setOnClickListener { finishWithResult("Lifetime Subscription") }
            }

            override fun animateLayout() {
                AnimationHelper.animateGroup(
                        findViewById(R.id.monthly),
                        findViewById(R.id.three_month),
                        findViewById(R.id.yearly),
                        findViewById(R.id.lifetime)
                )
            }
        })

    private fun finishWithResult(result: String) {
        val data = Intent()
        data.putExtra(RESULT_DATA_TEXT, result)

        setResult(Activity.RESULT_OK, data)
        finishAnimated()
    }

    companion object {
        val RESULT_DATA_TEXT = "result_data_text"
    }
}