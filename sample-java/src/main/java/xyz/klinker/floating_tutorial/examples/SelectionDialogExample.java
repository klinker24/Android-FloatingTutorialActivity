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
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import xyz.klinker.android.floating_tutorial.FloatingTutorialActivity;
import xyz.klinker.android.floating_tutorial.TutorialPage;
import xyz.klinker.floating_tutorial.R;
import xyz.klinker.floating_tutorial.util.AnimationHelper;

public class SelectionDialogExample extends FloatingTutorialActivity {

    public static final String RESULT_DATA_TEXT = "result_text";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    @NotNull
    @Override
    public List<TutorialPage> getPages() {
        List<TutorialPage> pages = new ArrayList<>();

        pages.add(new TutorialPage(this) {
            @Override
            public void initPage() {
                setContentView(R.layout.example_selection_dialog);
                setNextButtonText(R.string.cancel);

                findViewById(R.id.item_one).setOnClickListener(createItemClickedListener("item 1"));
                findViewById(R.id.item_two).setOnClickListener(createItemClickedListener("item 2"));
            }

            @Override
            public void animateLayout() {
                AnimationHelper.animateGroup(
                        findViewById(R.id.bottom_text),
                        findViewById(R.id.item_one),
                        findViewById(R.id.item_two)
                );
            }
        });

        return pages;
    }

    private View.OnClickListener createItemClickedListener(final String resultText) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultData = new Intent();
                resultData.putExtra(RESULT_DATA_TEXT, resultText);
                setResult(Activity.RESULT_OK, resultData);

                finishAnimated();
            }
        };
    }
}
