/**
 * Copyright 2021 Google Inc. All Rights Reserved.
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
package com.google.firebase.example.perfmon;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.perf.metrics.Trace;

public class TestActivity extends AppCompatActivity {

    // TODO (1): Start trace recording as soon as the Activity object is created.

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Current Activity's main view (as defined in the layout xml file) is inflated after this
        setContentView(R.layout.activity_test);

        // Firebase Performance record screen traces for Activities out-of-the-box. But it only does
        // that for hardware accelerated views. Hardware acceleration is enabled by default if your
        // Target API level is >=14, but can also be explicitly enabled.
        ScreenTrace.enableHardwareAcceleration(this);

        // TODO (2): Get the view added by Activity's setContentView() method.

        // Loading some GIF images to mimic slow rendering
        TestUtils.loadImage(this, findViewById(R.id.img_top), TestUtils.FIREBASE_LOGO_ANIMATION_GIF);
        TestUtils.loadImage(this, findViewById(R.id.img_center), TestUtils.FIREBASE_SPARKY_PINEAPPLE_GIF);

        // Performing some CPU intensive work on the Main Thread to mimic slow loading
        TestUtils.performIntensiveIoOperations(this);

        // TODO (3): Register the callback to listen for first frame rendering (see
        //  "OnFirstDrawCallback" in FirstDrawListener) and stop the trace when View drawing is
        //  finished.
    }
}
