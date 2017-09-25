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

package xyz.klinker.android.floating_tutorial.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import xyz.klinker.android.floating_tutorial.R
import xyz.klinker.android.floating_tutorial.util.DensityConverter

class ProgressIndicatorView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val defaultSize = DensityConverter.toDp(context, 6).toFloat()
    private val activatedSize = DensityConverter.toDp(context, 9).toFloat()

    private var paint = Paint()
    var current: Boolean = false
        set(value) {
            field = value
            invalidate()
        }

    init {
        paint.color = resources.getColor(R.color.tutorial_light_background_indicator)
    }

    /**
     * Set the color of the [ProgressIndicatorView].
     */
    fun setColor(color: Int) {
        paint.color = color
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        if (current) {
            canvas?.drawCircle(activatedSize / 2, activatedSize / 2, activatedSize / 2, paint)
        } else {
            canvas?.drawCircle(defaultSize / 2, defaultSize / 2, defaultSize / 2, paint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (current) {
            setMeasuredDimension(activatedSize.toInt(), activatedSize.toInt())
        } else {
            setMeasuredDimension(defaultSize.toInt(), defaultSize.toInt())
        }
    }

}