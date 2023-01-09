package com.example.stateexam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.DEBUG_PROPERTY_VALUE_OFF

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            HomeScreen()
        }
    }
}

@Composable
fun HomeScreen(viewModel: MainViewModel = viewModel()) {
    val (text: String, setText: (String) -> Unit) = remember {
        mutableStateOf("Hello World")
    }

    var text2 by remember {
        mutableStateOf("Hello World")
    }

    val text1: MutableState<String> = remember {
        mutableStateOf("Hello World")
    }

    val text3: State<String> = viewModel.liveData.observeAsState("Hello World")

    Column() {
        Text(text = "Hello World")
        Button(onClick = {
            text1.value = "ABC"
            print(text1.value)
            text2 = "ABC"
            print(text2)
            setText("ABC")
            viewModel.setValue("ABC")

        }) {
            Text(text = "클릭")
        }
        TextField(value = text3.value, onValueChange = {viewModel.setLiveValue(it)})
    }
}

class MainViewModel : ViewModel() {
    private val _value = mutableStateOf("Hello Wolrd")
    val value: State<String> = _value

    private val _liveData = MutableLiveData<String>()
    val liveData: LiveData<String> = _liveData

    fun setValue(msg: String){
        _value.value = msg
    }

    fun setLiveValue(msg: String){
        _liveData.value = msg
    }
}