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
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.perf.metrics.Trace;

public class TestFragment extends Fragment {

    // TODO (1): Declare the Trace variable.

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // TODO (2): Start trace recording as soon as the Fragment is attached to its host Activity.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View mainView, Bundle savedInstanceState) {
        super.onViewCreated(mainView, savedInstanceState);

        Activity parentActivity = getActivity();

        // Load some images
        TestUtils.loadImage(parentActivity, mainView.findViewById(R.id.img_top), TestUtils.FIREBASE_LOGO_ANIMATION_GIF);
        TestUtils.loadImage(parentActivity, mainView.findViewById(R.id.img_center), TestUtils.FIREBASE_SPARKY_PINEAPPLE_GIF);

        // Performing some CPU intensive work on the Main Thread to mimic slow loading
        TestUtils.performIntensiveIoOperations(parentActivity);

        // TODO (3): Register the callback to listen for first frame rendering (see
        //  "OnFirstDrawCallback" in FirstDrawListener) and stop the trace when View drawing is
        //  finished.
    }

}
