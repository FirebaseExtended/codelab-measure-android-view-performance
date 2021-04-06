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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    // Declares the Fragment tag.
    private static final String FRAGMENT_TAG = TestFragment.class.getSimpleName();

    // TODO (1): Declare the ScreenTrace variable.

    private RelativeLayout mainLayout;
    private RelativeLayout fragmentLayout;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.main_layout);
        fragmentLayout = findViewById(R.id.fragment_layout);

        mainLayout.setVisibility(View.VISIBLE);
        fragmentLayout.setVisibility(View.GONE);

        // TODO (2): Initialize the ScreenTrace variable.
    }

    public void loadActivity(View view) {
        Toast.makeText(this, "Loading Activity...", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, TestActivity.class));
    }

    public void loadFragment(View view) {
        Toast.makeText(this, "Loading Fragment...", Toast.LENGTH_SHORT).show();

        mainLayout.setVisibility(View.GONE);
        fragmentLayout.setVisibility(View.VISIBLE);

        // Create a FragmentManager
        fragmentManager = getSupportFragmentManager();

        // Create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the FrameLayout with the new Fragment
        fragmentTransaction
                .replace(R.id.frame_layout, new TestFragment(), FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();

        // Register for fragment lifecycle callbacks
        fragmentManager.registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, true);
    }

    private final FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks =
            new FragmentManager.FragmentLifecycleCallbacks() {

                @Override
                public void onFragmentAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
                    super.onFragmentAttached(fm, f, context);
                    Log.d(MyApp.LOG_TAG, "onFragmentAttached(): " + f);

                    // TODO (3): Start recording the screen traces as soon as the Fragment is
                    // attached to its host Activity.
                }

                @Override
                public void onFragmentDetached(@NonNull FragmentManager fm, @NonNull Fragment f) {
                    super.onFragmentDetached(fm, f);
                    Log.d(MyApp.LOG_TAG, "onFragmentDetached(): " + f);

                    // TODO (4): Stop recording the screen traces as soon as the Fragment is
                    // detached from its host Activity.

                    // Unregister fragment lifecycle callbacks after the fragment is detached
                    fm.unregisterFragmentLifecycleCallbacks(fragmentLifecycleCallbacks);
                }
            };

    @Override
    public void onBackPressed() {
        if (fragmentLayout.getVisibility() == View.VISIBLE) {
            fragmentManager.popBackStack();

            mainLayout.setVisibility(View.VISIBLE);
            fragmentLayout.setVisibility(View.GONE);

        } else {
            super.onBackPressed();
        }
    }
}