package com.example.moviedatabase.presentation.screen.popular

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moviedatabase.databinding.ActivityPopularBinding

class PopularActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPopularBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPopularBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                Color.TRANSPARENT,
            )
        )
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}