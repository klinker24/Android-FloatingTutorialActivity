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

package xyz.klinker.android.floating_tutorial.suite

import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import xyz.klinker.android.floating_tutorial.BuildConfig

@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(16, 19, 21, 26), constants = BuildConfig::class)
abstract class FloatingTutorialRobolectricSuite {

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

}
