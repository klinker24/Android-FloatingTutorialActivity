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

import org.junit.Test
import org.robolectric.RuntimeEnvironment

import org.junit.Assert.*
import xyz.klinker.android.floating_tutorial.suite.FloatingTutorialRobolectricSuite

class DensityConverterTest : FloatingTutorialRobolectricSuite() {

    // Robolectric uses a medium density scale (mdpi)
    // https://github.com/robolectric/robolectric/blob/master/robolectric/src/test/java/org/robolectric/shadows/ShadowDisplayTest.java

    // so, we can expect that the same numbers we plug in, should be the output for these functions
    // https://pixplicity.com/dp-px-converter/

    @Test
    fun test_toPx() {
        assertEquals(1000, DensityConverter.toPx(RuntimeEnvironment.application, 1000).toLong())
        assertEquals(10, DensityConverter.toPx(RuntimeEnvironment.application, 10).toLong())
        assertEquals(0, DensityConverter.toPx(RuntimeEnvironment.application, 0).toLong())
    }

    @Test(expected = IllegalArgumentException::class)
    fun test_toPx_error() {
        assertEquals(-1, DensityConverter.toPx(RuntimeEnvironment.application, -1).toLong())
    }

    @Test
    fun test_toDp() {
        assertEquals(1000, DensityConverter.toDp(RuntimeEnvironment.application, 1000).toLong())
        assertEquals(10, DensityConverter.toDp(RuntimeEnvironment.application, 10).toLong())
        assertEquals(0, DensityConverter.toDp(RuntimeEnvironment.application, 0).toLong())
    }

    @Test(expected = IllegalArgumentException::class)
    fun test_toDp_error() {
        assertEquals(-1, DensityConverter.toDp(RuntimeEnvironment.application, -1).toLong())
    }

}
