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
    
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status
    
    private val _catfacts = MutableLiveData<MutableList<CatFact>>()
    val catfacts: LiveData<MutableList<CatFact>> = _catfacts
    
    init {
        _catfacts.value = mutableListOf()
        getCatFact()
    }
    
    private fun getCatFact() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            Log.e("Abhi", "Fetching")
            try {
                for (i in 0..25) {
                    val tempList = _catfacts.value
                    tempList?.add(Api.retrofitService.getCatFact())
                    _catfacts.value = tempList
                }
                Log.e("Abhi viewmodel: \n", catfacts.value!!.joinToString("\n") { it.fact })
                _status.value = ApiStatus.DONE
                Log.e("Abhi", "Done")
            } catch (exception: Exception) {
                _status.value = ApiStatus.ERROR
                Log.e("Abhi", "Error: $exception")
            }
        }
    }
}
