package com.makeappssimple.abhimanyu.catfact.android.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.makeappssimple.abhimanyu.catfact.android.network.Api
import com.makeappssimple.abhimanyu.catfact.android.network.CatFact
import com.makeappssimple.abhimanyu.catfact.android.network.CatFactPagingSource
import kotlinx.coroutines.flow.Flow

class HomeViewModel : ViewModel() {
    
    val pagedCatFacts: Flow<PagingData<CatFact>> = Pager(PagingConfig(pageSize = 10)) {
        CatFactPagingSource(Api.retrofitService)
    }.flow.cachedIn(viewModelScope)
}
