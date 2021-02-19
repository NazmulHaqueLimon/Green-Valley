package com.example.greenvalley.ui.listItems

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.greenvalley.dataRepository.QueryItem
import com.example.greenvalley.databinding.ItemListBinding

class ItemListAdapter : ListAdapter<QueryItem<DisplayItem>, RecyclerView.ViewHolder>(PlantDiffCallback()){

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
        val qItem =getItem(position).item
        (holder as ListItemViewHolder).bind(qItem)
    }


    class ListItemViewHolder(
            private val binding: ItemListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.item?.let { item ->
                    //navigateToPlant(item, it)
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

        fun bind(qItem: DisplayItem) {
            binding.apply {
                item =qItem
                executePendingBindings()
            }
        }
    }
}

private class PlantDiffCallback : DiffUtil.ItemCallback<QueryItem<DisplayItem>>() {


    override fun areItemsTheSame(oldItem: QueryItem<DisplayItem>, newItem: QueryItem<DisplayItem>): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: QueryItem<DisplayItem>, newItem: QueryItem<DisplayItem>): Boolean {
        return oldItem == newItem
    }
}
