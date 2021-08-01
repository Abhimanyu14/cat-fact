package com.makeappssimple.abhimanyu.catfact.android.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.makeappssimple.abhimanyu.catfact.android.network.CatFact

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<CatFact>?) {
    val adapter = recyclerView.adapter as MainActivityRecyclerViewAdapter
    adapter.submitList(data)
}
