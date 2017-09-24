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

package xyz.klinker.floating_tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import xyz.klinker.floating_tutorial.examples.InAppPurchaseExample;
import xyz.klinker.floating_tutorial.examples.RateItExample;
import xyz.klinker.floating_tutorial.examples.SelectionDialogExample;
import xyz.klinker.floating_tutorial.examples.SimpleDialogExample;
import xyz.klinker.floating_tutorial.examples.TutorialExample;

public class SampleActivity extends AppCompatActivity {

    private static final int REQUEST_SELECTION = 1;
    private static final int REQUEST_RATE_IT = 2;
    private static final int REQUEST_PURCHASE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.simple_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SampleActivity.this, SimpleDialogExample.class));
            }
        });

        findViewById(R.id.selection_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(SampleActivity.this, SelectionDialogExample.class), REQUEST_SELECTION);
            }
        });

        findViewById(R.id.feature_tutorial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SampleActivity.this, TutorialExample.class));
            }
        });

        findViewById(R.id.rate_it).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(SampleActivity.this, RateItExample.class), REQUEST_RATE_IT);
            }
        });

        findViewById(R.id.iap_flow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(SampleActivity.this, InAppPurchaseExample.class), REQUEST_PURCHASE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECTION && resultCode == RESULT_OK) {
            Toast.makeText(this, data.getStringExtra(SelectionDialogExample.RESULT_DATA_TEXT), Toast.LENGTH_SHORT).show();
        } else if (requestCode == REQUEST_RATE_IT && resultCode == RESULT_OK) {
            Toast.makeText(this, data.getStringExtra(RateItExample.RESULT_DATA_TEXT), Toast.LENGTH_SHORT).show();
        } else if (requestCode == REQUEST_PURCHASE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Purchase Selected: ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No selection made", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
