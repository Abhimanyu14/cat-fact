package com.makeappssimple.abhimanyu.catfact.android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.makeappssimple.abhimanyu.catfact.android.R

class MainActivity : AppCompatActivity() {
    
    private val viewModel: MainActivityViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        viewModel.catfact.observe(this, {
            Log.e("Abhi", it.fact)
            findViewById<TextView>(R.id.fact).text = it.fact
        })
    }
}
