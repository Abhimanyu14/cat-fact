package com.makeappssimple.abhimanyu.catfact.android.utils

import android.os.Build
import com.makeappssimple.abhimanyu.catfact.android.BuildConfig

fun isDebugBuild() = BuildConfig.DEBUG

fun getBuildVersion() = Build.VERSION.SDK_INT

fun getBuildVersionName() = BuildConfig.VERSION_NAME

fun isAndroidApiEqualToOrAbove(buildVersionNumber: Int) = getBuildVersion() >= buildVersionNumber
