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

package xyz.klinker.floating_tutorial.examples;

import java.util.ArrayList;
import java.util.List;

import xyz.klinker.android.floating_tutorial.BottomSheetTutorialPage;
import xyz.klinker.android.floating_tutorial.FloatingTutorialActivity;
import xyz.klinker.android.floating_tutorial.TutorialPage;
import xyz.klinker.floating_tutorial.R;
import xyz.klinker.floating_tutorial.util.AnimationHelper;

public class BottomSheetExample extends FloatingTutorialActivity {

    @Override
    public List<TutorialPage> getPages() {
        List<TutorialPage> pages = new ArrayList<>();

        pages.add(new BottomSheetTutorialPage(this) {
            @Override
            public void initPage() {
                setContentView(R.layout.example_feature_walkthrough_page_1);
                setBackgroundColorResource(R.color.darkBackgroundColor);
            }

            @Override
            public void animateLayout() {
                AnimationHelper.animateGroup(
                        findViewById(R.id.feature_one_image),
                        findViewById(R.id.bottom_text)
                );
            }
        });

        pages.add(new BottomSheetTutorialPage(this) {
            @Override
            public void initPage() {
                setContentView(R.layout.example_feature_walkthrough_page_2);
            }

            @Override
            public void animateLayout() {
                AnimationHelper.animateGroup(
                        findViewById(R.id.bottom_text),
                        findViewById(R.id.feature_two_image)
                );
            }
        });

        pages.add(new BottomSheetTutorialPage(this) {
            @Override
            public void initPage() {
                setContentView(R.layout.example_feature_walkthrough_page_3);
                setBackgroundColorResource(R.color.colorPrimary);
            }

            @Override
            public void animateLayout() {
                AnimationHelper.animateGroup(
                        findViewById(R.id.feature_three_image_one),
                        findViewById(R.id.feature_three_image_two),
                        findViewById(R.id.bottom_text)
                );
            }
        });

        return pages;
    }
}