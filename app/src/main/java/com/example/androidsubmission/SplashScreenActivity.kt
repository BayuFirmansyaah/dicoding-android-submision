package com.example.androidsubmission

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        lifecycleScope.launch {
           toMainActivity()
        }
    }

    suspend fun toMainActivity(){
        delay(2000L)
        val intentMain = Intent(this@SplashScreenActivity, MainActivity::class.java)
        startActivity(intentMain)
        finish()
    }
}