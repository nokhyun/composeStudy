package com.example.ui

import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class WorkingActivity : ComponentActivity() {

    private val sensorManager by lazy { getSystemService(SensorManager::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val workingViewModel: WorkingViewModel = viewModel()

            MaterialTheme {
                Text("Hi")
            }
        }
    }
}

class WorkingViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

}