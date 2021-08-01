package com.makeappssimple.abhimanyu.catfact.android.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.makeappssimple.abhimanyu.catfact.android.R
import com.makeappssimple.abhimanyu.catfact.android.databinding.RecyclerviewItemLayoutBinding
import com.makeappssimple.abhimanyu.catfact.android.network.CatFact

class MainActivityRecyclerViewAdapter :
    PagingDataAdapter<CatFact, MainActivityRecyclerViewAdapter.MainActivityRecyclerViewHolder>(
        CatFactDiffCallback
    ) {
    
    class MainActivityRecyclerViewHolder(private var binding: RecyclerviewItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(catFact: CatFact?) {
            catFact?.let {
                binding.recyclerviewItemLayoutTextviewFact.text = String.format(
                    binding.root.context.resources.getString(R.string.recyclerview_item_layout_fact),
                    catFact.id,
                    catFact.fact
                )
            }
        }
    }
    
    object CatFactDiffCallback : DiffUtil.ItemCallback<CatFact>() {
        override fun areItemsTheSame(oldItem: CatFact, newItem: CatFact): Boolean {
            return oldItem.id == newItem.id
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
