package com.makeappssimple.abhimanyu.catfact.android.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.makeappssimple.abhimanyu.catfact.android.databinding.RecyclerviewItemLayoutBinding
import com.makeappssimple.abhimanyu.catfact.android.network.CatFact

class MainActivityRecyclerViewAdapter :
    ListAdapter<CatFact, MainActivityRecyclerViewAdapter.MainActivityRecyclerViewHolder>(
        CatFactDiffCallback
    ) {
    
    class MainActivityRecyclerViewHolder(private var binding: RecyclerviewItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(catFact: CatFact) {
            binding.recyclerviewItemLayoutTextviewFact.text = catFact.fact
        }
    }
    
    object CatFactDiffCallback : DiffUtil.ItemCallback<CatFact>() {
        override fun areItemsTheSame(oldItem: CatFact, newItem: CatFact): Boolean {
            return oldItem.fact == newItem.fact
        }
        
        override fun areContentsTheSame(oldItem: CatFact, newItem: CatFact): Boolean {
            return oldItem == newItem
        }
    }
    
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainActivityRecyclerViewHolder {
        return MainActivityRecyclerViewHolder(
            RecyclerviewItemLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }
    
    override fun onBindViewHolder(holder: MainActivityRecyclerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
