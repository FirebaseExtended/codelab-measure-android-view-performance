# Measuring Load time and Screen Rendering with Firebase Performance

This repo contains the source code to be used for the Codelab for Google I/O 2021.

Supported Features
------------------

## Activity/Fragment load time

This is measured in 2 phases. 

In the first phase we measure the time when the view tree is about to be drawn. This is done by 
registering a callback to [ViewTreeObserver.OnDrawListener](https://developer.android.com/reference/android/view/ViewTreeObserver.OnDrawListener). 
Getting this callback for the very first time (**first frame**) means that the complete UI 
(_including all views in the view hierarchy_) have been measured, laid out and given a frame. 
The drawing operation happens immediately after this callback.

In the second phase we measure the time when that **first frame** view is completely drawn. This is 
done by posting a message to the front of [MessageQueue](https://developer.android.com/reference/android/os/MessageQueue) 
of the [Looper](https://developer.android.com/reference/android/os/Looper#getMainLooper()) 
associated with the Main/UI thread ([reference](https://dev.to/pyricau/android-vitals-first-draw-time-m1d)).

**Note:** This is how Android framework measures the app startup time i.e by measuring the time from 
creating the app object until when the first frame completely loads and drawn on the screen 
([reference](https://developer.android.com/topic/performance/vitals/launch-time#cold)).

## Fragment Screen Rendering (Slow/Frozen frames)

FirebasePerformance automatically records Screen Rendering data for Activities 
([reference](https://firebase.google.com/docs/perf-mon/screen-traces?platform=android)) however, it 
does not record this information for Fragments. This is because the 
[FrameMetricsAggregator](https://developer.android.com/reference/androidx/core/app/FrameMetricsAggregator) 
API records the duration of rendering for an entire window and not a part of window.

We can however record those metrics for the duration when the Fragment is active. This is done by 
start recording the frames as soon as the Fragment is attached to the screen and stop recording as 
soon as the Fragment is detached. 

The core logic for Slow/Frozen frames is copied from 
[Fireperf Source Code](https://github.com/firebase/firebase-android-sdk/blob/d18cc40d39c7c3a0b7df107d16b1c686b51d195f/firebase-perf/src/main/java/com/google/firebase/perf/application/AppStateMonitor.java). 
This also means we create the trace name with [SCREEN_TRACE_PREFIX](https://github.com/firebase/firebase-android-sdk/blob/d18cc40d39c7c3a0b7df107d16b1c686b51d195f/firebase-perf/src/main/java/com/google/firebase/perf/util/Constants.java#L53) 
and attach [frame metrics](https://github.com/firebase/firebase-android-sdk/blob/d18cc40d39c7c3a0b7df107d16b1c686b51d195f/firebase-perf/src/main/java/com/google/firebase/perf/application/AppStateMonitor.java#L334-L343) 
so as to show the screen rendering information for the Fragment on the Firebase Console.

**Note:** If Fragment is covering only a part of the Screen than the reported metric will be skewed 
as it's impossible to know (from that API) what part of the screen contributes to those results.

## Credits
 - [John Reck](https://github.com/jreck) (Google) for providing useful feedback on the various 
 approaches on how we can measure the load time of Activity.
 
 - [This](https://dev.to/pyricau/android-vitals-rising-to-the-first-drawn-surface-1j9e) and 
 [this](https://dev.to/pyricau/android-vitals-first-draw-time-m1d) posts from Py.
