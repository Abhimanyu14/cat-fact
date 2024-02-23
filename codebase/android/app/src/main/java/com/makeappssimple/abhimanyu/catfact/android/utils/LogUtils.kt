package com.makeappssimple.abhimanyu.catfact.android.utils

import android.util.Log

fun logError(
    message: String,
) {
    if (isDebugBuild()) {
        Log.e("Abhi", message)
    }
}
