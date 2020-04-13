package se.yobriefca.deliveries.model

data class Position(val lat: Float, val lng: Float)

data class Place(val id: Int, val name: String, val postion: Position)

data class Category(val id: String, val name: String)
