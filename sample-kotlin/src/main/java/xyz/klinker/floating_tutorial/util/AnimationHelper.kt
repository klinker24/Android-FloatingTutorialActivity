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

package xyz.klinker.floating_tutorial.util

import android.view.View

import xyz.klinker.android.floating_tutorial.util.DensityConverter

object AnimationHelper {
    fun animateGroup(vararg views: View) {
        var startTime = 300

        for (v in views) {
            quickViewReveal(v, startTime.toLong())
            startTime += 75
        }
    }

    fun quickViewReveal(view: View, delay: Long) {
        view.translationX = -1f * DensityConverter.toDp(view.context, 16)
        view.alpha = 0f
        view.visibility = View.VISIBLE

        view.animate()
                .translationX(0f)
                .alpha(1f)
                .setStartDelay(delay)
                .start()
    }
}
