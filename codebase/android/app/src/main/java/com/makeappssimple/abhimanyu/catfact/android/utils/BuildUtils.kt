package com.makeappssimple.abhimanyu.catfact.android.utils

import com.makeappssimple.abhimanyu.catfact.android.BuildConfig

fun isDebugBuild(): Boolean {
    return BuildConfig.DEBUG
}

fun getBuildVersionName(): String {
    return BuildConfig.VERSION_NAME
}
