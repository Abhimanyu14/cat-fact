package com.makeappssimple.abhimanyu.catfact.android.network

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState

class CatFactPagingSource(
    private val apiService: ApiService,
) : PagingSource<Int, CatFact>() {
    
    var tempKey = 1
    
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, CatFact> {
        return try {
            val apiResponse = apiService.getCatFact()
            apiResponse.id = tempKey
            LoadResult.Page(
                data = listOf(apiResponse),
                prevKey = null,
                nextKey = tempKey++,
            )
        } catch (e: Exception) {
            Log.e("Abhi", "Error in loading data : $e")
            LoadResult.Error(e)
        }
    }
    
    override fun getRefreshKey(state: PagingState<Int, CatFact>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
