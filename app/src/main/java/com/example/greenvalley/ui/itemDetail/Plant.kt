package com.example.greenvalley.ui.itemDetail

data class Plant(
        val plantId :String,
        val name :String,
        val description :String,
        val watering :String,
        val pruning :String,
        val lighting :String,
        val soilProcessing:String,
        val PlantingGuide :String,
        val wateringInterval :Int =7,
        val imageUrl :String=""
)
