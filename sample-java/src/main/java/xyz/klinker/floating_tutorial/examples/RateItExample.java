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

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import xyz.klinker.android.floating_tutorial.FloatingTutorialActivity;
import xyz.klinker.android.floating_tutorial.TutorialPage;
import xyz.klinker.floating_tutorial.R;
import xyz.klinker.floating_tutorial.util.AnimationHelper;

public class RateItExample extends FloatingTutorialActivity {

    public static final String RESULT_DATA_TEXT = "result_data_text";

    @NotNull
    @Override
    public List<TutorialPage> getPages() {
        List<TutorialPage> pages = new ArrayList<>();

        pages.add(new PageOne(this));
        pages.add(new PageTwo(this));

        return pages;
    }

    private static class PageOne extends TutorialPage {
        public PageOne(@NotNull FloatingTutorialActivity activity) {
            super(activity);
        }

        @Override
        public void initPage() {
            setContentView(R.layout.example_rate_it_page_1);
            hideNextButton();

            findViewById(R.id.thumbs_up).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    setPageResultData(RateItResult.THUMBS_UP);
                    getActivity().onNextPressed();
                }
            });

            findViewById(R.id.thumbs_down).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    setPageResultData(RateItResult.THUMBS_DOWN);
                    getActivity().onNextPressed();
                }
            });
        }

        @Override
        public void onShown(boolean firstTimeShown) {
            super.onShown(firstTimeShown);
            setActivityResult(Activity.RESULT_CANCELED);
        }

        @Override
        public void animateLayout() {
            AnimationHelper.animateGroup(
                    findViewById(R.id.bottom_text),
                    findViewById(R.id.thumbs_up),
                    findViewById(R.id.thumbs_down)
            );
        }
    }

    private static class PageTwo extends TutorialPage {
        public PageTwo(@NotNull FloatingTutorialActivity activity) {
            super(activity);
        }

        @Override
        public void initPage() {
            setContentView(R.layout.example_rate_it_page_2);
        }

        @Override
        public void onShown(boolean firstTimeShown) {
            super.onShown(firstTimeShown);

            TextView titleText = (TextView) findViewById(R.id.top_text);
            TextView summaryText = (TextView) findViewById(R.id.bottom_text);

            RateItResult previousResult = (RateItResult) getPreviousPageResult();
            if (previousResult == RateItResult.THUMBS_UP) {
                titleText.setText(R.string.rate_it_page_2_good_title);
                summaryText.setText(R.string.rate_it_page_2_good_summary);

                Intent data = new Intent();
                data.putExtra(RESULT_DATA_TEXT, "Send that user to the Play Store to give you a rating!");
                setActivityResult(Activity.RESULT_OK, data);

                setNextButtonText(R.string.rate_it);
            } else {
                titleText.setText(R.string.rate_it_page_2_bad_title);
                summaryText.setText(R.string.rate_it_page_2_bad_summary);

                Intent data = new Intent();
                data.putExtra(RESULT_DATA_TEXT, "They have feedback for your app. Maybe allow them to send you an email?");
                setActivityResult(Activity.RESULT_OK, data);

                setNextButtonText(R.string.send_email);
            }
        }

        @Override
        public void animateLayout() {
            AnimationHelper.quickViewReveal(findViewById(R.id.bottom_text), 300);
        }
    }

    private enum RateItResult {
        THUMBS_UP, THUMBS_DOWN
    }
}