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

package xyz.klinker.android.floating_tutorial.util

import android.content.Context
import android.util.TypedValue

/**
 * A utility class to help with the translation from pixels to density pixels, to support
 * the vast array of Android screen sizes.
 */
object DensityConverter {

    /**
     * Convert a DP value back to pixels.

     * @param context the application context.
     * *
     * @param dp the density pixels to convert.
     * *
     * @return the associated pixel value.
     */
    fun toPx(context: Context, dp: Int): Int {
        return convert(context, dp, TypedValue.COMPLEX_UNIT_PX)
    }

    /**
     * Convert a PX value to density pixels.

     * @param context the application context.
     * *
     * @param px the pixels to convert.
     * *
     * @return the associated density pixel value.
     */
    fun toDp(context: Context, px: Int): Int {
        return convert(context, px, TypedValue.COMPLEX_UNIT_DIP)
    }

    private fun convert(context: Context, amount: Int, conversionUnit: Int): Int {
        if (amount < 0) {
            throw IllegalArgumentException("px should not be less than zero")
        }

        val r = context.resources
        return TypedValue.applyDimension(conversionUnit, amount.toFloat(), r.displayMetrics).toInt()
    }
}