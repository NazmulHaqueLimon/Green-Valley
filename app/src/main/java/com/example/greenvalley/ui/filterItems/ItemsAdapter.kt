package com.example.greenvalley.ui.filterItems

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.provider.DocumentsContract
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.annotation.RequiresApi
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.greenvalley.R
import com.example.greenvalley.databinding.ItemChipBinding



class ItemsAdapter (context: Context) :ListAdapter<FilterItem, ItemsAdapter.ItemsViewHolder>(ItemDiff) {

    @SuppressLint("ResourceType")
    private val selectedTint = context.getColor(R.drawable.item_tint_selector)
    private val topLeftCornerRadius= context.resources?.getDimensionPixelSize(R.dimen.small_component_top_left_radius)
    @SuppressLint("UseCompatLoadingForDrawables")
    private val selectedDrawable= context.getDrawable(R.drawable.ic_checkmark)

   // private val selectedItems: MutableList<String>?=null
    val selectedItems = mutableListOf<String>()
    var tracker: SelectionTracker<Long>? =null
    init {
        setHasStableIds(true)
    }

    @JvmName("getSelectedItems1")
    fun getSelectedItems():MutableList<String> {
        /**if (selectedItems ==null){
           Log.d(TAG,"items list is null")
        }*/
         if (selectedItems.size >0){
            Log.d(TAG,"items list is totally null null........")
        }
        return selectedItems

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val binding = ItemChipBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false)
                /**.apply{
                    root.setOnClickListener {
                        it.isActivated = !it.isActivated

                    }
        }*/
        return ItemsViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        var item=getItem(position)
        if (selectedDrawable != null && topLeftCornerRadius !=null) {
           // tracker?.let {
               holder.itemView.setOnClickListener {
                   it.isActivated=!it.isActivated
                   selectedItems.add(item.name)
               }
                holder.bind (item, selectedTint, topLeftCornerRadius, selectedDrawable)

           // } it.isSelected(position.toLong())

        }
    }

    override fun onViewRecycled(holder: ItemsViewHolder) {
        holder.itemView.rotation = 0f
    }

   inner class ItemsViewHolder (
            private val binding: ItemChipBinding
    ):RecyclerView.ViewHolder(binding.root),View.OnClickListener{


        fun bind(
                item: FilterItem,
            //isActivated:Boolean=false,
                @ColorInt selectedTint: Int,
                @Px selectedTopLeftCornerRadius: Int,
                selectedDrawable: Drawable,
                selected: Boolean=false)
        {
            //itemView.isActivated =selected
            //selectedItems?.add(item.name)


            binding.run {

                //if (root.)

                this.filterItems=item

                Glide.with(itemImage)
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

                executePendingBindings()
            }
        }

       override fun onClick(v: View?) {
           val element =getItem(layoutPosition)
           selectedItems.add(element.name)
       }

   }


}


