package com.example.moviedatabase.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviedatabase.data.remote.response.PopularMovieItem
import com.example.moviedatabase.data.remote.retrofit.ApiService

class PopularMoviePagingSource(
    private val apiService: ApiService
): PagingSource<Int, PopularMovieItem>() {
    private companion object {
        const val INTIAL_PAGE = 1
    }

    override fun getRefreshKey(state: PagingState<Int, PopularMovieItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PopularMovieItem> {
        return try {
            val position = params.key ?: INTIAL_PAGE
            val responseData = apiService.getPopularMovies(position)

            LoadResult.Page(
                data = responseData.results,
                prevKey = if (position == INTIAL_PAGE) null else position - 1,
                nextKey = if (responseData.results.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
     }
}