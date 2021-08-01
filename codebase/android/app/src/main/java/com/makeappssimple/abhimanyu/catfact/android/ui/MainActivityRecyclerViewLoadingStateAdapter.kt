package com.makeappssimple.abhimanyu.catfact.android.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.makeappssimple.abhimanyu.catfact.android.databinding.RecyclerviewLoadingStateLayoutBinding

class MainActivityRecyclerViewLoadingStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<MainActivityRecyclerViewLoadingStateAdapter.MainActivityRecyclerViewLoadingStateViewHolder>() {
    
    inner class MainActivityRecyclerViewLoadingStateViewHolder(private var binding: RecyclerviewLoadingStateLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            binding.recyclerviewLoadingStateLayoutButtonRetry.isVisible =
                loadState !is LoadState.Loading
            binding.recyclerviewLoadingStateLayoutTextviewError.isVisible =
                loadState !is LoadState.Loading
            binding.recyclerviewLoadingStateLayoutProgressbar.isVisible =
                loadState is LoadState.Loading
            if (loadState is LoadState.Error) {
                binding.recyclerviewLoadingStateLayoutTextviewError.text =
                    loadState.error.localizedMessage
            }
            
            binding.recyclerviewLoadingStateLayoutButtonRetry.setOnClickListener {
                retry.invoke()
            }
        }
    }
    
    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MainActivityRecyclerViewLoadingStateViewHolder {
        return MainActivityRecyclerViewLoadingStateViewHolder(
            RecyclerviewLoadingStateLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }
    
    override fun onBindViewHolder(
        holder: MainActivityRecyclerViewLoadingStateViewHolder,
        loadState: LoadState
    ) {
        holder.bind(loadState)
    }
}
