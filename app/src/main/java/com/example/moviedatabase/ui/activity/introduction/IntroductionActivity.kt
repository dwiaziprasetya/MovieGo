package com.example.moviedatabase.ui.activity.introduction

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedatabase.R
import com.example.moviedatabase.databinding.ActivityIntroductionBinding
import com.example.moviedatabase.ui.activity.main.MainActivity
import com.example.moviedatabase.ui.adapter.ImageGridAdapter

class IntroductionActivity : AppCompatActivity() {

    private lateinit var adapter: ImageGridAdapter
    private lateinit var itemList: ArrayList<Int>

    private lateinit var binding : ActivityIntroductionBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                android.graphics.Color.TRANSPARENT,
            )
        )

        super.onCreate(savedInstanceState)


        actionBar?.hide()

        binding = ActivityIntroductionBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.rvIntroduction.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                val itemCount = layoutManager.itemCount
                if (lastVisibleItem >= itemCount - 1) {
                    recyclerView.scrollToPosition(0)
                }
            }
        })

        binding.rvIntroduction.setOnTouchListener { _, _ -> true  }

        binding.btnGetStarted.setOnClickListener {
            startActivity(Intent(this@IntroductionActivity, MainActivity::class.java))
        }

        init()
        startSmoothContinuousScroll()
    }

    private fun init() {
        itemList = ArrayList()
        binding.rvIntroduction.setHasFixedSize(true)
        binding.rvIntroduction.layoutManager = GridLayoutManager(this, 2)
        addImageToList()
        adapter = ImageGridAdapter(itemList)
        binding.rvIntroduction.adapter = adapter
    }

    private fun addImageToList() {
        itemList.add(R.drawable.image_poster_1)
        itemList.add(R.drawable.image_poster_2)
        itemList.add(R.drawable.image_poster_3)
        itemList.add(R.drawable.image_poster_4)
        itemList.add(R.drawable.image_poster_5)
        itemList.add(R.drawable.image_poster_1)
        itemList.add(R.drawable.image_poster_2)
        itemList.add(R.drawable.image_poster_3)
        itemList.add(R.drawable.image_poster_4)
        itemList.add(R.drawable.image_poster_5)
    }

    private fun startSmoothContinuousScroll() {
        val animator = ValueAnimator.ofInt(0, 10000)
        animator.duration = 10000L
        animator.addUpdateListener { _ ->
            binding.rvIntroduction.scrollBy(0, 3)
        }
        animator.repeatCount = ValueAnimator.INFINITE
        animator.start()
    }
}