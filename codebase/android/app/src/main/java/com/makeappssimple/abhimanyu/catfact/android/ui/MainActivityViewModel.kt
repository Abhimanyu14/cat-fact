package com.makeappssimple.abhimanyu.catfact.android.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.catfact.android.network.Api
import com.makeappssimple.abhimanyu.catfact.android.network.CatFact
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE }

class MainActivityViewModel : ViewModel() {
    
    private val _apiStatus = MutableLiveData<ApiStatus>()
    val apiStatus: LiveData<ApiStatus> = _apiStatus
    
    private val _catFacts = MutableLiveData<MutableList<CatFact>>()
    val catFacts: LiveData<MutableList<CatFact>> = _catFacts
    
    init {
        _catFacts.value = mutableListOf()
        getCatFact()
    }
    
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
}
