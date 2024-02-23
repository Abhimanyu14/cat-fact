package com.makeappssimple.abhimanyu.catfact.android.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.makeappssimple.abhimanyu.catfact.android.network.datasource.CatFactPagingSource
import com.makeappssimple.abhimanyu.catfact.android.network.model.CatFact
import com.makeappssimple.abhimanyu.catfact.android.network.service.Api
import kotlinx.coroutines.flow.Flow

class HomeViewModel : ViewModel() {
    val pagedCatFacts: Flow<PagingData<CatFact>> = Pager(
        config = PagingConfig(
            pageSize = 10,
        ),
    ) {
        CatFactPagingSource(
            apiService = Api.retrofitService,
        )
    }.flow.cachedIn(
        scope = viewModelScope,
    )
}
