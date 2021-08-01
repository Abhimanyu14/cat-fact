package com.makeappssimple.abhimanyu.catfact.android.network.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.makeappssimple.abhimanyu.catfact.android.network.model.CatFact
import com.makeappssimple.abhimanyu.catfact.android.network.service.ApiService

class CatFactPagingSource(
    private val apiService: ApiService,
) : PagingSource<Int, CatFact>() {
    
    private var localKey = 1
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, CatFact> {
        return try {
            val apiResponse = apiService.getCatFact().apply { id = localKey++ }
            LoadResult.Page(
                data = listOf(apiResponse),
                prevKey = null,
                nextKey = if (localKey < 16) {
                    localKey
                } else {
                    null
                },
            )
        } catch (e: Exception) {
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
