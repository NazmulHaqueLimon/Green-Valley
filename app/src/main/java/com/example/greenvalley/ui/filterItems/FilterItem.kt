package com.example.greenvalley.ui.filterItems


import androidx.recyclerview.widget.DiffUtil
import com.example.greenvalley.R

data class FilterItem(
        val name:String,
        val total:Int,
        val background : Int
)
object ItemDiff : DiffUtil.ItemCallback<FilterItem>() {
    override fun areItemsTheSame(oldItem: FilterItem, newItem: FilterItem) = oldItem.name == newItem.name
    override fun areContentsTheSame(oldItem: FilterItem, newItem: FilterItem) = oldItem == newItem
}
val filterItems = listOf(
        FilterItem("Flower Plants", 58, R.drawable.coconut_tree),
        FilterItem("Fruit Plants", 121, R.drawable.alovera),
        FilterItem("Medicinal Plants", 78, R.drawable.coconut_tree),
        FilterItem("Seeds", 118, R.drawable.alovera),
        FilterItem("Tools", 423, R.drawable.coconut_tree),
        FilterItem("Soil", 92, R.drawable.coconut_tree),
        FilterItem("Fertilizers", 165, R.drawable.alovera),
        FilterItem("Indoor Plants", 164, R.drawable.coconut_tree),
        FilterItem("Decoration", 326, R.drawable.alovera),
        FilterItem("Cactus", 305, R.drawable.alovera),
        FilterItem("Water Savior", 305, R.drawable.alovera),
        FilterItem("Law Light", 305, R.drawable.coconut_tree)

)