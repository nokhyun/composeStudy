package com.nokhyun.library

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun Context.hasPostNotification(manifest: String) =
    ContextCompat.checkSelfPermission(this, manifest) == PackageManager.GET_URI_PERMISSION_PATTERNS