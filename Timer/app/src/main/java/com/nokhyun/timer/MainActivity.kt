package com.nokhyun.timer

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nokhyun.library.hasPostNotification
import com.nokhyun.timer.ui.theme.TimerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            postNotification()
        }
    }

    @Composable
    private fun postNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionCheck(manifest = android.Manifest.permission.POST_NOTIFICATIONS) { isGranted ->
                // TODO FCM Setting
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    private fun permissionCheck(
        manifest: String,
        onResult: (Boolean) -> Unit
    ) {
        when {
            this.hasPostNotification(manifest) -> {
                // TODO FCM Setting
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS) -> {
                // TODO ALERT DIALOG
            }
            else -> {
                val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission(), onResult = onResult)
                permissionLauncher.launch(manifest)
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TimerTheme {
        Greeting("Android")
    }
}