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

package xyz.klinker.floating_tutorial

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast

import xyz.klinker.floating_tutorial.examples.FeatureWalkthroughExample
import xyz.klinker.floating_tutorial.examples.PulseSmsPurchaseExample
import xyz.klinker.floating_tutorial.examples.RateItExample
import xyz.klinker.floating_tutorial.examples.SelectionDialogExample
import xyz.klinker.floating_tutorial.examples.SimpleDialogExample

class SampleActivity : AppCompatActivity() {

    private val simpleDialog: View by lazy { findViewById<View>(R.id.simple_dialog) }
    private val selectionDialog: View by lazy { findViewById<View>(R.id.selection_dialog) }
    private val featureTutorial: View by lazy { findViewById<View>(R.id.feature_tutorial) }
    private val rateIt: View by lazy { findViewById<View>(R.id.rate_it) }
    private val iapFlow: View by lazy { findViewById<View>(R.id.iap_flow) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        simpleDialog.setOnClickListener {
            startActivity(Intent(this, SimpleDialogExample::class.java))
        }

        selectionDialog.setOnClickListener {
            startActivityForResult(Intent(this, SelectionDialogExample::class.java), REQUEST_SELECTION)
        }

        featureTutorial.setOnClickListener {
            startActivity(Intent(this, FeatureWalkthroughExample::class.java))
        }

        rateIt.setOnClickListener {
            startActivityForResult(Intent(this, RateItExample::class.java), REQUEST_RATE_IT)
        }

        iapFlow.setOnClickListener {
            startActivityForResult(Intent(this, PulseSmsPurchaseExample::class.java), REQUEST_PURCHASE)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when(requestCode) {
            REQUEST_SELECTION -> showToast(data?.getStringExtra(SelectionDialogExample.RESULT_DATA_TEXT))
            REQUEST_RATE_IT -> showToast(data?.getStringExtra(RateItExample.RESULT_DATA_TEXT))
            REQUEST_PURCHASE -> showToast("Purchase Selected: " + data?.getStringExtra(PulseSmsPurchaseExample.RESULT_DATA_TEXT))
        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private val REQUEST_SELECTION = 1
        private val REQUEST_RATE_IT = 2
        private val REQUEST_PURCHASE = 3
    }
}
