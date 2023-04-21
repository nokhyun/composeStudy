package com.example.ui

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.view.ViewTreeObserver.OnPreDrawListener
import android.view.animation.AnticipateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.nio.charset.Charset
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class MainActivity : AppCompatActivity() {

    private var isReady = false

    //    private val btnWorking: Button by lazy { findViewById(R.id.btnWorking) }
    private val ivTest: ImageView by lazy { findViewById(R.id.ivTest) }
    private val rvTest: RecyclerView by lazy { findViewById(R.id.rvTest) }
    private val testAdapter = TestAdapter()

    private val serviceIntent by lazy { Intent(this@MainActivity, TestService::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Service
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Timber.e("[onCreate] Service Start")
            startForegroundService(serviceIntent)
        }

        init()
        lifecycleScope.launch {
            val logging = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }

//            val imageLoader = ImageLoader.Builder(this@MainActivity)
//                .okHttpClient {
//                    OkHttpClient.Builder()
//                        .addNetworkInterceptor(logging)
//                        .addInterceptor (
//                            Interceptor {
//                                it.proceed(it.request())
//                            }
//                        )
//                        .build()
//                }
//                .build()
//
//            val request = ImageRequest.Builder(this@MainActivity)
//                .data("url")
//                .setHeader(
//                    "Authorization",
//                    "TOKEN"
//                )
//                .target(ivTest)
//                .build()
//
//            imageLoader.execute(request = request)
        }
//
//
//        btnWorking.setOnClickListener {
//            startActivity(Intent(this@MainActivity, WorkingActivity::class.java))
//        }

//        splashScreen.setKeepOnScreenCondition { true }
        // ... Activity 이동.
//        startActivity(Intent(this, SecondActivity::class.java))
//        finish()

//        newSplashScreen()   // 신규 앱에 스플래시 화면 추가

//        val masterKey = MasterKey.Builder(applicationContext, MasterKey.DEFAULT_MASTER_KEY_ALIAS).build()
        val key = "aaaaaassssdddfghaaaaaassssdddfgh"
        val encodeResult = Crypt().encode("asd", key)
        Timber.e("crypt encode: $encodeResult")
        Timber.e("crypt decode: ${Crypt().decode(encodeResult, key)}")

        val user = user {
            name = "da"
        }

        Timber.e("user: $user")
    }

    fun user(userBlock: User.() -> Unit): User {
        return User().also(userBlock)
    }

    data class User(
        var name: String = ""
    )

    private fun newSplashScreen() {
        val content: ConstraintLayout = findViewById(R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(object : OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                Timber.e("isReady: $isReady")
                return if (isReady) {
                    content.viewTreeObserver.removeOnPreDrawListener(this)
                    true
                } else {
                    false
                }
            }
        })

        content.postDelayed({
            isReady = true
        }, 2000)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                val slideUp = ObjectAnimator.ofFloat(
                    splashScreenView,
                    View.TRANSLATION_Y,
                    0f,
                    -splashScreenView.height.toFloat()
                )

                slideUp.interpolator = AnticipateInterpolator()
                slideUp.duration = 200L

                slideUp.doOnEnd { splashScreenView.remove() }

                slideUp.start()
            }
        }
    }

    private fun init() {
        rvTest.apply {
            adapter = testAdapter
            setHasFixedSize(true)
        }
    }

    override fun onDestroy() {
        stopService(serviceIntent)
        super.onDestroy()
    }
}

class Crypt {

    fun encode(str: String, key: String): String {
        val textByte = str.toByteArray()
        val newKey = SecretKeySpec(key.toByteArray(), "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, newKey, getIvSpec())
        return Base64.encodeToString(cipher.doFinal(textByte), Base64.DEFAULT)!!
    }

    fun decode(str: String, key: String): String {
        val textBytes = Base64.decode(str, Base64.DEFAULT)
        val newKey = SecretKeySpec(key.toByteArray(), "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, newKey, getIvSpec())
        return String(cipher.doFinal(textBytes), Charset.forName("UTF-8"))
    }

    /** Extentions 로 만들면 개꿀이겠죠.? */
    fun base64Encode(str: String) = Base64.encodeToString(str.toByteArray(), Base64.DEFAULT)
    fun base64Decode(str: String) = String(Base64.decode(str, Base64.DEFAULT), Charset.forName("UTF-8"))


    private fun getIvSpec() = IvParameterSpec(ivBytes)

    companion object {
        private val ivBytes = byteArrayOf(
            0x00,
            0x00,
            0x00,
            0x00,
            0x00,
            0x00,
            0x00,
            0x00,
            0x00,
            0x00,
            0x00,
            0x00,
            0x00,
            0x00,
            0x00,
            0x00
        )
    }
}