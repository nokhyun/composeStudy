package com.example.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.doOnAttach
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class TestAdapter : RecyclerView.Adapter<TestAdapterViewHolder>() {

    private var urlList = mutableListOf<String>().apply {
        repeat(300){
            add(it.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestAdapterViewHolder {
        return TestAdapterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_image_item, parent, false))
    }

    override fun getItemCount(): Int = urlList.size
    override fun onBindViewHolder(holder: TestAdapterViewHolder, position: Int) {
        holder.iv.doOnAttach {
            it.findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
                val imageLoader = ImageLoader.Builder(it.context)
                    .okHttpClient{
                        OkHttpClient.Builder()
                            .addNetworkInterceptor(logging)
                            .build()
                    }.build()

                val request = ImageRequest.Builder(it.context)
                    .data(url)
                    .setHeader(
                        "Authorization",
                        token
                    ).target(holder.iv)
                    .build()

                imageLoader.execute(request)
            }
        }
    }

    companion object {
        private const val url = "https://test.doctorvice.co.kr/test.png"
        private const val token =
            "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJBZXN3aWpZRV9jZVd6by10YlBNNmMwNFBydU1FQkNWQ1ktVDdDdkpiOG9vIn0.eyJleHAiOjE2ODk1NTgyNDksImlhdCI6MTY4MTc4MjI0OSwianRpIjoiMGYyYWVlNzgtOThlNy00ZTIyLTlkZTQtNjA0NzhlM2JiY2I1IiwiaXNzIjoiaHR0cHM6Ly9kM3Ytb2F1dGguaWtvb2IuY28ua3IvYXV0aC9yZWFsbXMvaWtvb2IiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiY2M0MjFhZTUtMDE0NS00MDlhLWI0MjgtOTE3NzVhMTA0ZWU3IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZG9jdG9ydmljZSIsInNlc3Npb25fc3RhdGUiOiJlNzUyZTE0NC0yZGZlLTQyMTktOTVjOS1hYjk0ODc0NzJhZmMiLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRvY3RvciIsImRlZmF1bHQtcm9sZXMtZG9jdG9ydmljZS1wcm8iLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBsb2dpbl90eXBlIHByb2ZpbGUiLCJzaWQiOiJlNzUyZTE0NC0yZGZlLTQyMTktOTVjOS1hYjk0ODc0NzJhZmMiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsInByZWZlcnJlZF91c2VybmFtZSI6ImMxM2Y4NzVmZDkzZTQ1MjE4YTFhYzRiYzQ1ZjZjYTk1X2RvY3RvcjEwMDAifQ.QuexTBiZ-GF5QxWdwcbeOiXOpA6t3EeMqG086-DyPr8bObT09FiLLTQwj-jI-7h0BVNIG5H1O1DmyQUXVcZe5owiJmPmai8wy7PecOPb_ZUfhG83dhFA07V7Z0WYCqwbhUky21OJllx1Ow-61Negdiwe8KvfwDHb0nfeUm86Kv3nNE1upu8n39pV3e5p06R98SEBmaBVnZif1FluLGcO3zmUNsG78Bl0uUOsvMRIpT5INsM5a1HaqIt-wxeS2N2Kol57NZ42lGRMCWjyGTKP8wRWC79PNr8N_O8JQYScqph1_BBxC6nCHTHSCCl-CNi1xFn6ovZEnAgmWxnjXLpdyA"

        private val logging = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }
}

class TestAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val iv: ImageView by lazy { view.findViewById(R.id.ivServerImage) }
}
