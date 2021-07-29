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
    
    private val _catfact = MutableLiveData<CatFact>()
    val catfact: LiveData<CatFact> = _catfact
    
    init {
        Log.e("Abhi", "View model created")
        getCatFact()
    }
    
    private fun getCatFact() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            Log.e("Abhi", "Fetching")
            try {
                _catfact.value = Api.retrofitService.getCatFact()
                _status.value = ApiStatus.DONE
                Log.e("Abhi", "Done")
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _catfact.value = CatFact("", -1)
                Log.e("Abhi", "Error: $e")
            }
        }
    }
}
