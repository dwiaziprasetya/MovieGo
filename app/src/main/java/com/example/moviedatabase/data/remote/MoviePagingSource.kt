package com.example.moviedatabase.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviedatabase.data.remote.response.DiscoverMovieItem
import com.example.moviedatabase.data.remote.retrofit.ApiService

class MoviePagingSource(
    private val apiService: ApiService
) : PagingSource<Int, DiscoverMovieItem>() {

    private companion object {
        const val INITIAL_PAGE = 1
    }

    override fun getRefreshKey(state: PagingState<Int, DiscoverMovieItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DiscoverMovieItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE
            val responseData = apiService.getDiscoverMovies(position)

            LoadResult.Page(
                data = responseData.results,
                prevKey = if (position == INITIAL_PAGE) null else position - 1,
                nextKey = if (responseData.results.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}