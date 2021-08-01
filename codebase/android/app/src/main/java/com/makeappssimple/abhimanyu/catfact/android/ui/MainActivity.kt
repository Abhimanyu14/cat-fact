package com.makeappssimple.abhimanyu.catfact.android.ui

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.makeappssimple.abhimanyu.catfact.android.databinding.ActivityMainBinding
import com.makeappssimple.abhimanyu.catfact.android.utils.ConnectivityLiveData

class MainActivity : AppCompatActivity() {
    
    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.activityMainRecyclerView.adapter = MainActivityRecyclerViewAdapter()
        
        viewModel.apiStatus.observe(this, { status ->
            binding.activityMainProgressbar.visibility = if (status == ApiStatus.LOADING) {
                VISIBLE
            } else {
                GONE
            }
        })
        
        ConnectivityLiveData(this).observe(this, { networkState ->
            // TODO: Use 'networkState' to detect connectivity
        })
    }
}
