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

import android.app.Activity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.FrameMetricsAggregator;
import androidx.core.util.Preconditions;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.perf.metrics.Trace;
import com.google.firebase.perf.util.Constants;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class TestUtils {

    public static final String FIREBASE_LOGO_ANIMATION_GIF = "https://storage.googleapis.com/stgd/firebase/uST1OoQhwSPLeZ94ZPDAZ63Ayiq1/ce982c30-f114-11e9-9e62-1fe2556ebf85.gif";
    public static final String FIREBASE_SPARKY_PINEAPPLE_GIF = "https://media.giphy.com/media/S3QB5UrHpH4Kq5wsax/giphy.gif";

    /**
     * Loads an image pointed by the {@code url} onto the {@code imageView}.
     */
    public static void loadImage(Activity activity, ImageView imageView, String url) {
        Log.d(MyApp.LOG_TAG, "<<< Loading Image >>>");

        Glide.with(activity)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);
    }

    /**
     * Does some heavy lifting CPU work.
     */
    public static void performIntensiveIoOperations(Activity activity) {
        Log.d(MyApp.LOG_TAG, "<<< I/O START >>>");

        File outFile = new File(activity.getFilesDir(), "test_out_file.txt");

        if(outFile.exists() && outFile.delete()) {
            // Deleting the old file so we don't fill up device's storage unnecessarily
            Log.d(MyApp.LOG_TAG, "Deleting previous file at " + outFile.getAbsolutePath());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {

            int fileSize = 30 * 1000 * 1000; // 30 MB
            byte[] buffer = new byte[fileSize];
            new Random().nextBytes(buffer);

            writer.write(new String(buffer));

        } catch (IOException e) {
            Log.d(MyApp.LOG_TAG, "IO Exception during reading: " + e);
        } finally {
            Log.d(MyApp.LOG_TAG, "<<< I/O FINISH >>>");
        }
    }

}
