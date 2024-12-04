package com.example.moviedatabase.ui.activity.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.moviedatabase.BuildConfig
import com.example.moviedatabase.R
import com.example.moviedatabase.data.remote.response.DetailMovieResponse
import com.example.moviedatabase.databinding.ActivityDetailBinding
import com.example.moviedatabase.ui.adapter.RvCastMovieAdapter
import com.example.moviedatabase.utils.ViewModelFactory

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {

    companion object {
        const val MOVIE_ID = "movie_id"
    }

    private lateinit var binding : ActivityDetailBinding
    private var adapter = RvCastMovieAdapter()
    private lateinit var factory: ViewModelFactory
    private val viewModel by viewModels<DetailViewModel> {factory}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window,false)

        handleWindowInsets()

        val movieId = intent.getIntExtra(MOVIE_ID, -1)

        factory = ViewModelFactory.getInstance(movieId)


        binding.imgBtnBack.setOnClickListener {
            onBackPressed()
        }

        setUpData()
    }

    private fun handleWindowInsets() {
        binding.root.setOnApplyWindowInsetsListener { view, insets ->
            val navigationBarHeight = insets.systemGestureInsets.bottom
            binding.detailActivity.setPadding(
                view.paddingLeft,
                0,
                view.paddingRight,
                navigationBarHeight
            )
            insets
        }
    }

    private fun setUpData() {
        viewModel.movieDetail.observe(this) { movie ->
            movie?.let { populateUI(it) }
        }

        viewModel.movieCredits.observe(this) { credits ->
            credits?.let {
                adapter.submitList(it.cast)
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                binding.pb.visibility = View.VISIBLE
            } else {
                binding.pb.visibility = View.GONE
                setUpAnimation()
            }
        }

        viewModel.isSetting.observe(this) { isSetting ->
            if (isSetting) {
                binding.cnsDetail.visibility = View.VISIBLE
                binding.btnWatchTrailer.visibility = View.VISIBLE
            } else {
                binding.btnWatchTrailer.visibility = View.GONE
                binding.cnsDetail.visibility = View.GONE
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun populateUI(detail: DetailMovieResponse) {
        val imageBaseUrl = BuildConfig.BASE_IMAGE_URL_MOVIE_DB_ORIGINAL
        binding.tvDetailMovieName.text = detail.title
        binding.ratingBar.rating = (detail.voteAverage / 2).toFloat()
        binding.tvDetailMovieGenre.text = "${detail.releaseDate.substring(0, 4)} | ${detail.genres.take(2).joinToString(", ") { it.name }} | ${formatRuntime(detail.runtime)}"

        binding.tvDetailMovieOverviewData.text = detail.overview

        binding.rvCastMovie.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvCastMovie.hasFixedSize()
        binding.rvCastMovie.adapter = adapter
        Glide.with(baseContext)
            .load(imageBaseUrl + detail.backdropPath)
            .fitCenter()
            .into(binding.imgDetailMoviePhoto)
    }

    private fun setUpAnimation(){
        binding.tvDetailMovieName.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_up))
        binding.tvDetailMovieGenre.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_up))
        binding.tvDetailMovieOverview.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_up))
        binding.tvDetailMovieOverviewData.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_up))
        binding.rvCastMovie.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_up))
        binding.btnWatchTrailer.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_up))
        binding.imgBtnBack.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_up))
    }

    private fun formatRuntime(runtime: Int): String {
        val hours = runtime / 60
        val minutes = runtime % 60
        return "$hours h $minutes m"
    }
}