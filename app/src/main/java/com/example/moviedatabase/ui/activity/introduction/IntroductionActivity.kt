@file:Suppress("DEPRECATION")

package com.example.moviedatabase.ui.activity.introduction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import com.example.moviedatabase.databinding.ActivityIntroductionBinding
import com.example.moviedatabase.ui.activity.main.MainActivity

class IntroductionActivity : AppCompatActivity() {

    private lateinit var binding : ActivityIntroductionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroductionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window,false)

        binding.btnGetStarted.setOnClickListener {
            startActivity(Intent(this@IntroductionActivity, MainActivity::class.java))
        }
    }
}