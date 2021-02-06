package com.example.greenvalley.ui.listItems

/**
 * A container class for displaying properly formatted stock price data.
 */
data class DisplayItem (
        //val item_id :String,
        val name :String
        )
/**
 * Converts a StockPrice object into a StockPriceDisplay object.
 */
fun Item.toDisplayItem()=DisplayItem(
        //this.item_id,
        this.name
)