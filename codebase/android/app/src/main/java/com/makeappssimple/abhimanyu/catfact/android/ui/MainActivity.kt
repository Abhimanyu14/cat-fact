package com.makeappssimple.abhimanyu.catfact.android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
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
        
        viewModel.catfact.observe(this, { catfact ->
            Log.e("Abhi", catfact.fact)
            binding.fact.text = catfact.fact
        })
        
        ConnectivityLiveData(this).observe(this, { networkState ->
            // TODO: Use 'networkState' to detect connectivity
        })
    }
}
