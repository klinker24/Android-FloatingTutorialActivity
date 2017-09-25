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
import android.os.Build
import android.os.Bundle
import android.view.View

import java.util.ArrayList

import xyz.klinker.android.floating_tutorial.FloatingTutorialActivity
import xyz.klinker.android.floating_tutorial.TutorialPage
import xyz.klinker.floating_tutorial.R
import xyz.klinker.floating_tutorial.util.AnimationHelper

@Suppress("DEPRECATION")
class SelectionDialogExample : FloatingTutorialActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = resources.getColor(R.color.colorPrimaryDark)
        }
    }

    override fun getPages(): List<TutorialPage> {
        return listOf(object : TutorialPage(this@SelectionDialogExample) {
            override fun initPage() {
                setContentView(R.layout.example_selection_dialog)
                setNextButtonText(R.string.cancel)

                findViewById<View>(R.id.item_one).setOnClickListener(createItemClickedListener("item 1"))
                findViewById<View>(R.id.item_two).setOnClickListener(createItemClickedListener("item 2"))
            }

            override fun animateLayout() {
                AnimationHelper.animateGroup(
                        findViewById(R.id.bottom_text),
                        findViewById(R.id.item_one),
                        findViewById(R.id.item_two)
                )
            }
        })
    }

    private fun createItemClickedListener(resultText: String) = View.OnClickListener {
        val resultData = Intent()
        resultData.putExtra(RESULT_DATA_TEXT, resultText)
        setResult(Activity.RESULT_OK, resultData)

        finishAnimated()
    }

    companion object {
        val RESULT_DATA_TEXT = "result_text"
    }
}
