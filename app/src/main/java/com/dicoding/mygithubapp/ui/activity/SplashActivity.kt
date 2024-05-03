package com.dicoding.mygithubapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.mygithubapp.R
import com.dicoding.mygithubapp.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

    // Waktu delay untuk splash screen (dalam milidetik)
    private val SPLASH_TIME_OUT: Long = 1000 // 1 detik

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val imgLogo: ImageView = findViewById(R.id.imageView)

        val zoomInAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_in)
        imgLogo.startAnimation(zoomInAnimation)

        // Handler untuk menangani penundaan pembukaan Activity berikutnya
        Handler().postDelayed({
            // Setelah penundaan, arahkan ke MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            // Tutup SplashActivity agar tidak kembali ke sini saat tombol back ditekan
            finish()
        }, SPLASH_TIME_OUT)
    }
}