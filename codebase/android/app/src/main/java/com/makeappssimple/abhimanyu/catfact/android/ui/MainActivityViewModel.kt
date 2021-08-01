package com.makeappssimple.abhimanyu.catfact.android.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

enum class ApiStatus { LOADING, ERROR, DONE }

class MainActivityViewModel : ViewModel() {
    
    // private val _apiStatus = MutableLiveData<ApiStatus>()
    // val apiStatus: LiveData<ApiStatus> = _apiStatus
    
    // private val _catFacts = MutableLiveData<MutableList<CatFact>>()
    // val catFacts: LiveData<MutableList<CatFact>> = _catFacts
    
    init {
        // _catFacts.value = mutableListOf()
        // getCatFact()
    }
    
    val pagedCatFacts: Flow<PagingData<CatFact>> = Pager(PagingConfig(pageSize = 10)) {
        CatFactPagingSource(Api.retrofitService)
    }.flow.cachedIn(viewModelScope)
    
    /*
    private fun getCatFact() {
        viewModelScope.launch {
            _apiStatus.value = ApiStatus.LOADING
            Log.e("Abhi", "Fetching")
            try {
                val tempList = mutableListOf<CatFact>()
                for (i in 0..10) {
                    tempList.add(Api.retrofitService.getCatFact())
                }
                catFacts.value?.forEachIndexed { index, catFact ->
                    Log.e("Abhi viewmodel:", "$index ${catFact.fact}")
                }
                _catFacts.value = tempList
                _apiStatus.value = ApiStatus.DONE
                Log.e("Abhi", "Done")
            } catch (exception: Exception) {
                _apiStatus.value = ApiStatus.ERROR
                Log.e("Abhi", "Error: $exception")
            }
        }
    }
    */
}
