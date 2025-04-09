package com.example.moviedatabase.presentation.screen.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.moviedatabase.BuildConfig
import com.example.moviedatabase.R
import com.example.moviedatabase.data.local.entity.Favourite
import com.example.moviedatabase.data.remote.response.DetailMovieResponse
import com.example.moviedatabase.databinding.ActivityDetailBinding
import com.example.moviedatabase.presentation.adapter.RvCastMovieAdapter
import com.example.moviedatabase.utils.TopSnackbar
import com.example.moviedatabase.utils.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {

    companion object {
        const val MOVIE_ID = "movie_id"
    }

    private lateinit var binding : ActivityDetailBinding
    private var adapter = RvCastMovieAdapter()
    private lateinit var factory: ViewModelFactory
    private val viewModel by viewModels<DetailViewModel> {factory}
    private var checkFavourite = false
    private lateinit var favourite: Favourite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                android.graphics.Color.TRANSPARENT,
            )
        )

        val movieId = intent.getIntExtra(MOVIE_ID, -1)

        factory = ViewModelFactory.getInstance(this, movieId)

        binding.imgBtnBack.setOnClickListener {
            onBackPressed()
        }

        setUpData()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(0, 0, 0, systemBars.bottom)
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
                binding.shimmerLayout.visibility = View.VISIBLE
                binding.btnWatchTrailerShimmer.visibility = View.VISIBLE
                binding.shimmerLayout.startShimmer()
            } else {
                binding.shimmerLayout.stopShimmer()
                binding.shimmerLayout.visibility = View.GONE
                setUpAnimation()
                binding.btnWatchTrailerShimmer.visibility = View.GONE
                binding.cnsDetail.visibility = View.VISIBLE
                binding.btnWatchTrailer.visibility = View.VISIBLE
            }
        }
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    private fun populateUI(movie: DetailMovieResponse) {
        val imageBaseUrl = BuildConfig.BASE_IMAGE_URL_MOVIE_DB_ORIGINAL
        binding.tvDetailMovieName.text = movie.title
        binding.ratingBar.rating = (movie.voteAverage / 2).toFloat()
        binding.tvDetailMovieGenre.text = "${movie.releaseDate.substring(0, 4)} | ${movie.genres.take(2).joinToString(", ") { it.name }} | ${formatRuntime(movie.runtime)}"

        binding.tvDetailMovieOverviewData.text = movie.overview

        favourite = Favourite(
            movieId = movie.id,
            movieName = movie.title,
            moviePhoto = movie.posterPath,
            genres = movie.genres.take(2).joinToString(", ") { it.name },
            releaseDate = movie.releaseDate,
            runtime = movie.runtime
        )

        binding.rvCastMovie.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvCastMovie.hasFixedSize()
        binding.rvCastMovie.adapter = adapter
        Glide.with(baseContext)
            .load(imageBaseUrl + movie.backdropPath)
            .override(800, 450)
            .fitCenter()
            .into(binding.imgDetailMoviePhoto)

        binding.btnFavourite.setOnClickListener {
            checkFavourite = !checkFavourite
            if (checkFavourite) {
                binding.icFavourite.setImageResource(R.drawable.icon_favourite_fill)
                viewModel.addToFavorite(favourite)

                val snackbar = TopSnackbar.make(
                    binding.root,
                    "${movie.title} added to Favourite",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.green))
                snackbar.show()
            } else {
                binding.icFavourite.setImageResource(R.drawable.icon_favourite)
                viewModel.deleteFromFavorite(movie.id)

                val snackbar = TopSnackbar.make(
                    binding.root,
                    "${movie.title} removed from Favourite",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.red_netflix))
                snackbar.show()
            }
        }

        viewModel.isMovieFavourite(movie.id).observe(this) { isFavourite ->
            checkFavourite = isFavourite
            if (isFavourite) {
                binding.icFavourite.setImageResource(R.drawable.icon_favourite_fill)
            } else {
                binding.icFavourite.setImageResource(R.drawable.icon_favourite)
            }
        }
    }

    private fun setUpAnimation(){
        binding.tvDetailMovieName.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_up))
        binding.tvDetailMovieGenre.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_up))
        binding.tvDetailMovieOverview.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_up))
        binding.tvDetailMovieOverviewData.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_up))
        binding.rvCastMovie.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_up))
        binding.tvMovieCast.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_up))
        binding.line2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_up))
        binding.llBottomButtons.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_up))
        binding.btnWatchTrailer.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_up))
        binding.imgBtnBack.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_up))
        binding.ratingBar.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_up))
    }

    private fun formatRuntime(runtime: Int): String {
        val hours = runtime / 60
        val minutes = runtime % 60
        return "$hours h $minutes m"
    }
}