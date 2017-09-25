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

import android.content.Intent;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import xyz.klinker.android.floating_tutorial.FloatingTutorialActivity;
import xyz.klinker.android.floating_tutorial.TutorialPage;
import xyz.klinker.floating_tutorial.R;
import xyz.klinker.floating_tutorial.util.AnimationHelper;

public class PulseSmsPurchaseExample extends FloatingTutorialActivity {

    public static final String RESULT_DATA_TEXT = "result_data_text";

    @NotNull
    @Override
    public List<TutorialPage> getPages() {
        List<TutorialPage> pages = new ArrayList<>();

        pages.add(new TutorialPage(this) {
            @Override
            public void initPage() {
                setContentView(R.layout.example_pulse_purchase_page_1);
                setNextButtonText(R.string.try_it);
            }

            @Override
            public void animateLayout() {
                AnimationHelper.quickViewReveal(findViewById(R.id.bottom_text), 450);
                AnimationHelper.animateGroup(
                        findViewById(R.id.icon_android),
                        findViewById(R.id.icon_phone),
                        findViewById(R.id.icon_computer),
                        findViewById(R.id.icon_tablet),
                        findViewById(R.id.icon_watch)
                );
            }
        });

        pages.add(new TutorialPage(this) {
            @Override
            public void initPage() {
                setContentView(R.layout.example_pulse_purchase_page_2);
                hideNextButton();

                findViewById(R.id.monthly).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finishWithResult("Monthly Subscription");
                    }
                });

                findViewById(R.id.three_month).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finishWithResult("Three Month Subscription");
                    }
                });

                findViewById(R.id.yearly).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finishWithResult("Yearly Subscription");
                    }
                });

                findViewById(R.id.lifetime).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finishWithResult("Lifetime Subscription");
                    }
                });
            }

            @Override
            public void animateLayout() {
                AnimationHelper.animateGroup(
                        findViewById(R.id.monthly),
                        findViewById(R.id.three_month),
                        findViewById(R.id.yearly),
                        findViewById(R.id.lifetime)
                );
            }
        });

        return pages;
    }

    private void finishWithResult(String result) {
        Intent data = new Intent();
        data.putExtra(RESULT_DATA_TEXT, result);

        setResult(RESULT_OK, data);
        finishAnimated();
    }
}