package com.example.alarmmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val scheduler = AndroidAlarmScheduler(this)
        var alarmItem: AlarmItem? = null
        setContent {
            MaterialTheme {
                var secondsText by remember {
                    mutableStateOf("")
                }
                var message by remember {
                    mutableStateOf("")
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    OutlinedTextField(
                        value = secondsText,
                        onValueChange = { secondsText = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(text = "Trigger alarm int seconds")
                        }
                    )

                    OutlinedTextField(
                        value = message,
                        onValueChange = { message = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(text = "Message")
                        }
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            modifier = Modifier.padding(end = 2.dp),
                            onClick = {
                                alarmItem = AlarmItem(
                                    time = LocalDateTime.now().plusSeconds(secondsText.toLong()),
                                    message = message
                                )
                                alarmItem?.let(scheduler::schedule)
                                secondsText = "0"
                                message = ""
                            }) {
                            Text(text = "Schedule")
                        }
                        Button(
                            modifier = Modifier.padding(start = 2.dp),
                            onClick = {
                                alarmItem?.let(scheduler::cancel)
                            }) {
                            Text(text = "Cancel")
                        }
                    }
                }
            }
        }
    }
}