package com.example.webview

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainViewModel = viewModel<MainViewModel>()
            HomeScreen(mainViewModel)
        }
    }
}

@Composable
fun HomeScreen(viewModel: MainViewModel) {
    val focusManager = LocalFocusManager.current
    val (inputUrl, setUrl) = rememberSaveable { mutableStateOf("https://www.google.com") }

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "나만의 웹 브라우저") },
                actions = {
                    IconButton(onClick = {
                        viewModel.undo()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                    IconButton(onClick = {
                        viewModel.redo()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            )
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .padding(it)
        ) {
            OutlinedTextField(
                value = inputUrl,
                onValueChange = setUrl,
                label = { Text(text = "https://") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    viewModel.url.value = inputUrl
                    focusManager.clearFocus()
                })
            )

            Spacer(modifier = Modifier.height(16.dp))

            MyWebView(viewModel, scaffoldState)
        }
    }
}

@Composable
fun MyWebView(
    viewModel: MainViewModel,
    scaffoldState: ScaffoldState
) {

    val scope = rememberCoroutineScope()
    val webView = rememberWebView()

//    LaunchedEffect(scaffoldState.snackbarHostState) {
//        viewModel.undoSharedFlow.collectLatest {
//            if (webView.canGoBack()) webView.goBack() else scaffoldState.snackbarHostState.showSnackbar("더 이상 뒤로 갈 수 없음")
//        }
//    }

    // 상태가 변화가 있으면 기존에 있던 작업내역을 날리고 마지막 작업만 실행함.
    LaunchedEffect(scaffoldState.snackbarHostState) {
        viewModel.redoSharedFlow.collectLatest {
            if (webView.canGoForward()) webView.goForward() else scaffoldState.snackbarHostState.showSnackbar("더 이상 앞으로 갈 수 없음")
        }
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            webView
        },
        update = { webview ->
            webview.loadUrl(viewModel.url.value)
            scope.launch {
                // 여러번 클릭 시 스낵바의 상태변화와 상관없이 작업내역을 전부 쌓아두고 실행(Queue)
                viewModel.undoSharedFlow.collectLatest {
                    if (webView.canGoBack()) webView.goBack() else scaffoldState.snackbarHostState.showSnackbar("더 이상 뒤로 갈 수 없음")
                }
            }
        }
    )
}

@Composable
fun rememberWebView(): WebView {
    val context = LocalContext.current
    val webView = remember {
        WebView(context).apply {
            settings.apply {
                javaScriptEnabled = true
                webViewClient = WebViewClient()
                webChromeClient = WebChromeClient()
            }
            loadUrl("https://google.com")
        }
    }

    return webView
}