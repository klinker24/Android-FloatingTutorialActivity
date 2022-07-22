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

import android.view.View
import xyz.klinker.android.floating_tutorial.BottomSheetTutorialPage
import java.util.ArrayList

import xyz.klinker.android.floating_tutorial.FloatingTutorialActivity
import xyz.klinker.android.floating_tutorial.TutorialPage
import xyz.klinker.floating_tutorial.R
import xyz.klinker.floating_tutorial.util.AnimationHelper

class BottomSheetExample : FloatingTutorialActivity() {

    override fun getPages() = listOf(
        object : BottomSheetTutorialPage(this@BottomSheetExample) {
            override fun initPage() {
                setContentView(R.layout.example_feature_walkthrough_page_1)
                setBackgroundColorResource(R.color.darkBackgroundColor)
            }

            override fun animateLayout() {
                AnimationHelper.animateGroup(
                        findViewById<View>(R.id.feature_one_image),
                        findViewById<View>(R.id.bottom_text)
                )
            }
        }, object : BottomSheetTutorialPage(this@BottomSheetExample) {
            override fun initPage() {
                setContentView(R.layout.example_feature_walkthrough_page_2)
            }

            override fun animateLayout() {
                AnimationHelper.animateGroup(
                        findViewById<View>(R.id.bottom_text),
                        findViewById<View>(R.id.feature_two_image)
                )
            }
        }, object : BottomSheetTutorialPage(this@BottomSheetExample) {
            override fun initPage() {
                setContentView(R.layout.example_feature_walkthrough_page_3)
                setBackgroundColorResource(R.color.colorPrimary)
            }

            override fun animateLayout() {
                AnimationHelper.animateGroup(
                        findViewById<View>(R.id.feature_three_image_one),
                        findViewById<View>(R.id.feature_three_image_two),
                        findViewById<View>(R.id.bottom_text)
                )
            }
        })
}