package com.example.greenvalley.ui.listItems

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.greenvalley.databinding.ItemListBinding

class ItemListAdapter : ListAdapter<Item, RecyclerView.ViewHolder>(PlantDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ListItemViewHolder(
                ItemListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item =getItem(position)
        (holder as ListItemViewHolder).bind(item)
    }


    class ListItemViewHolder(
            private val binding: ItemListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.item?.let { item ->
                    navigateToPlant(item, it)
                }
            }
        }

        private fun navigateToPlant(
                item: Item,
                view: View
        ) {
           /** val direction =
                   HomeViewPagerFragmentDirections.actionViewPagerFragmentToPlantDetailFragment(
                            item.item_id
                    )
            view.findNavController().navigate(direction) */
        }

        fun bind(plant: Item) {
            binding.apply {
                item = plant
                executePendingBindings()
            }
        }
    }
}

private class PlantDiffCallback : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.item_id == newItem.item_id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}
