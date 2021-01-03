package com.example.greenvalley.ui.filterItems

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.greenvalley.R
import com.example.greenvalley.databinding.ItemChipBinding


class ItemsAdapter(context: Context?) :ListAdapter<FilterItem,ItemsViewHolder>(ItemDiff) {



    @SuppressLint("ResourceType")
    private val selectedTint = context?.getColor(R.drawable.item_tint_selector)

    private val topLeftCornerRadius= context?.resources?.getDimensionPixelSize(R.dimen.small_component_top_left_radius)

    //private val selectedDrawable=
    @SuppressLint("UseCompatLoadingForDrawables")
    private val selectedDrawable= context?.getDrawable(R.drawable.ic_checkmark)


    private val selectedItems: MutableList<String>?=null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val binding = ItemChipBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false)

                .apply{
                    root.setOnClickListener {
                        it.isActivated = !it.isActivated

                    }
        }
        return ItemsViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        var item=getItem(position)
        if (selectedDrawable != null && selectedTint !=null && topLeftCornerRadius !=null) {
                holder.bind (item ,selectedTint, topLeftCornerRadius, selectedDrawable)

        }

        when(holder.itemView.isActivated){
            true ->{
                selectedItems?.add(item.name)
            }
            false ->{
                selectedItems?.remove(item.name)
            }
        }

    }

    override fun onViewRecycled(holder: ItemsViewHolder) {
        holder.itemView.rotation = 0f
    }

    fun getSelectedItemList(): MutableList<String>? {
        return selectedItems

    }


}

class ItemsViewHolder (
        private val binding: ItemChipBinding
        ):RecyclerView.ViewHolder(binding.root){

    fun bind(
        item: FilterItem,
        @ColorInt selectedTint: Int,
        @Px selectedTopLeftCornerRadius: Int,
        selectedDrawable: Drawable
    ) {
        binding.run {
            this.filterItem=item

            Glide.with(itemImage)
                .asBitmap()
                .load(item.background)

            executePendingBindings()
        }
    }
}
/**
 * Glide.with(itemImage)
.asBitmap()
.load(item.background)

.placeholder(R.drawable.ic_check_circle_24px)
.into(
ItemThumbnailTarget(
itemImage,
selectedTint,
selectedTopLeftCornerRadius,
selectedDrawable
)
)
 * */
/**when(b) {
true -> {}
false -> {}
null -> {}
}**/
