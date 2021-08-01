package com.makeappssimple.abhimanyu.catfact.android.ui

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.makeappssimple.abhimanyu.catfact.android.databinding.ActivityMainBinding
import com.makeappssimple.abhimanyu.catfact.android.utils.ConnectivityLiveData
import com.makeappssimple.abhimanyu.catfact.android.utils.NetworkState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    
    private val viewModel: MainActivityViewModel by viewModels()
    private val mainActivityRecyclerViewAdapter by lazy { MainActivityRecyclerViewAdapter() }
    private var initialConnectivityCheckCompleted: Boolean = false
    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.activityMainRecyclerView.adapter =
            mainActivityRecyclerViewAdapter.withLoadStateFooter(
                footer = MainActivityRecyclerViewLoadingStateAdapter {
                    mainActivityRecyclerViewAdapter.retry()
                }
            )
        
        ConnectivityLiveData(this).observe(this, { networkState ->
            if (networkState == NetworkState.CONNECTED) {
                binding.activityMainImageviewNoInternet.visibility = GONE
                binding.activityMainTextviewNoInternet.visibility = GONE
                binding.activityMainRecyclerView.visibility
                fetchCatFacts()
            } else {
                if (!initialConnectivityCheckCompleted) {
                    binding.activityMainImageviewNoInternet.visibility = VISIBLE
                    binding.activityMainTextviewNoInternet.visibility = VISIBLE
                }
            }
            initialConnectivityCheckCompleted = true
        })
    }
    
    private fun fetchCatFacts() {
        lifecycleScope.launch {
            viewModel.pagedCatFacts.collectLatest {
                mainActivityRecyclerViewAdapter.submitData(it)
            }
        }
    }
}
