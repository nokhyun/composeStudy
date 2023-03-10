package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@Composable
fun ImageCard(
    modifier: Modifier,
    isFavorite: Boolean,
    onTabFavorite: (Boolean) -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        elevation = 5.dp
    ) {
        Box(
            modifier = Modifier.height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "poster",
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopEnd
            ) {
                IconButton(onClick = {
                    onTabFavorite(!isFavorite)
                }) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "favorite",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}

@Composable
fun FirstScreen(navController: NavController) {
    val (value, setValue) = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "?????????")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("second") }) {
            Text(text = "??? ??????!")
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = value, onValueChange = setValue)
        Button(onClick = {
            if (value.isNotEmpty()) {
                navController.navigate("third/$value")
            }
        }) {
            Text(text = "??? ??????!")
        }
    }
}

@Composable
fun SecondScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "??? ?????? ??????")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigateUp() }) {
            Text(text = "?????? ??????!")
        }
    }
}

@Composable
fun ThirdScreen(navController: NavHostController, value: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "??? ?????? ??????")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = value)
        Button(onClick = { navController.navigateUp() }) {
            Text(text = "?????? ??????!")
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
class MainActivity : ComponentActivity() {

    private val testFlow = MutableSharedFlow<Long>(2)

//    private val viewModel by viewModels<MainViewModel>()

    val testflow1 = callbackFlow<Long> {
        send(System.currentTimeMillis())

        awaitClose()
    }.buffer(2)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                lifecycleScope.launch {
                    launch {
                        testFlow.emit(System.currentTimeMillis())
                    }
                }
            }
        })

//        val timeOut = 2000L
//        var backWait = 0L
//        lifecycleScope.launch {
//            launch {
//                testflow1.collectLatest {
//                    Log.e(this.javaClass.simpleName, "asda: $it")
//                }
//            }
//            launch {
//                testFlow
//                    .subscriptionCount
//                    .onEach {
//                        Toast.makeText(this@MainActivity, "Hi", Toast.LENGTH_SHORT).show()
//                    }
//                    .map {
//                        Log.e(this.javaClass.simpleName, "map: ${it}")
//                        it
//                    }
//                    .collect {
//                        Log.e(this.javaClass.simpleName, "asdasd: ${it}")
////                    if(it + timeOut >= System.currentTimeMillis()) finish()
//
//
////                    if (System.currentTimeMillis() - backWait >= timeOut) {
////                    if (backWait + timeOut <= System.currentTimeMillis()) {
////                    if (backWait + timeOut <= System.currentTimeMillis()) {
////                        backWait = System.currentTimeMillis()
////                    } else {
////                        finish()
////                    }
//                    }
//            }
//        }

        setContent {
            val viewModel = viewModel<MainViewModel>()
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = viewModel.data.value,
                    fontSize = 30.sp
                )
                Button(onClick = { viewModel.changeValue() }) {
                    Text(text = "??????")
                }
            }
        }

//        setContent {
//            val navController = rememberNavController()
//            NavHost(navController = navController, startDestination = "first") {
//                composable("first") {
//                    FirstScreen(navController)
//                }
//                composable("second") {
//                    SecondScreen(navController)
//                }
//                composable("third/{value}") {
//                    ThirdScreen(navController, it.arguments?.getString("value") ?: "")
//                }
//            }
//
//        }

//        setContent {
////            var textValue by remember { mutableStateOf("") }
//            val (textValue, setValue) = remember { mutableStateOf("") }
//
//            val scaffoldState = rememberScaffoldState()
//            val scope = rememberCoroutineScope()
//            val keyboardController = LocalSoftwareKeyboardController.current
//
//            Scaffold(
//                scaffoldState = scaffoldState
//            ) {
//                Column(
//                    modifier = Modifier.fillMaxSize(),
//                    verticalArrangement = Arrangement.Center,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    TextField(
//                        value = textValue,
//                        onValueChange = setValue,
//                        placeholder = { Text(text = "?????????!") },
//                        label = { Text(text = "label")}
//                    )
//                    Button(onClick = {
//                        keyboardController?.hide()
//                        scope.launch {
//                            scaffoldState.snackbarHostState.showSnackbar("Hello $textValue")
//                        }
//                    }) {
//                        Text(text = "??????")
//                    }
//                }
//            }
//        }

//        setContent {
//            var isFavorite by rememberSaveable { mutableStateOf(false) }
//
//            ImageCard(
//                modifier = Modifier
//                    .fillMaxWidth(0.5f)
//                    .padding(16.dp),
//                isFavorite
//            ) {
//                isFavorite = it
//            }
//        }

//        setContent {
////            MyApplicationTheme {
////                // A surface container using the 'background' color from the theme
////                Surface(modifier = Modifier, color = MaterialTheme.colors.background) {
////                    Column(
////                        modifier = Modifier
////                            .fillMaxSize()
////                            .background(color = Color.Blue)
////                            .padding(16.dp),
////                        horizontalAlignment = Alignment.CenterHorizontally,
////                    ) {
////                        Text("Hello")
////                        Text("World")
////                    }
////                }
////            }
////            Box(
////                modifier = Modifier
////                    .background(color = Color.Green)
////                    .fillMaxWidth()
////                    .height(200.dp),
////                contentAlignment = Alignment.TopEnd
////            ) {
////                Text(text = "Hello")
////                Box(
////                    modifier = Modifier
////                        .fillMaxSize()
////                        .padding(16.dp),
////                    contentAlignment = Alignment.BottomEnd
////                ) {
////                    Text(text = "Hello", modifier = Modifier.padding(start = 20.dp))
////                }
////            }
//            val scrollState = rememberScrollState()
//
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(color = Color.Green),
//                contentPadding = PaddingValues(16.dp),
//                verticalArrangement = Arrangement.spacedBy(8.dp)
////                    .verticalScroll(scrollState)
//            ) {
////                for (i in 0 until 50) {
////                    Text(text = i.toString(), modifier = Modifier.padding(12.dp))
////                }
//                item {
//                    Text(text = "Header")
//                }
//
//                items(50) { index ->
//                    Text(
//                        text = index.toString(), modifier = Modifier
//                            .background(Color.Gray)
//                            .width(100.dp),
//                        textAlign = TextAlign.Center,
//                    )
//                }
//                item {
//                    Text(text = "Footer")
//                }
//            }
//        }
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

class MainViewModel : ViewModel() {
    private val _data = mutableStateOf("Hello")
    val data: State<String> = _data

    fun changeValue(){
        _data.value = "World"
    }
}