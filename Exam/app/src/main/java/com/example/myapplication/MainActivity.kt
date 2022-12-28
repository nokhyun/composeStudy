package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            MyApplicationTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(modifier = Modifier, color = MaterialTheme.colors.background) {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .background(color = Color.Blue)
//                            .padding(16.dp),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                    ) {
//                        Text("Hello")
//                        Text("World")
//                    }
//                }
//            }
//            Box(
//                modifier = Modifier
//                    .background(color = Color.Green)
//                    .fillMaxWidth()
//                    .height(200.dp),
//                contentAlignment = Alignment.TopEnd
//            ) {
//                Text(text = "Hello")
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(16.dp),
//                    contentAlignment = Alignment.BottomEnd
//                ) {
//                    Text(text = "Hello", modifier = Modifier.padding(start = 20.dp))
//                }
//            }
            val scrollState = rememberScrollState()

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Green),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
//                    .verticalScroll(scrollState)
            ) {
//                for (i in 0 until 50) {
//                    Text(text = i.toString(), modifier = Modifier.padding(12.dp))
//                }
                item {
                    Text(text = "Header")
                }

                items(50) { index ->
                    Text(
                        text = index.toString(), modifier = Modifier
                            .background(Color.Gray)
                            .width(100.dp),
                        textAlign = TextAlign.Center,
                    )
                }
                item {
                    Text(text = "Footer")
                }
            }
        }
    }
}

@Composable
fun Greetings(name: String) {
    Text(text = name)
}

@Preview(showBackground = true)
@Composable
fun GreetingsPreview() {
    MyApplicationTheme {
        Greetings(name = "Android")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingsPreview2() {
    MyApplicationTheme {
        Greetings(name = "Android123")
    }
}
