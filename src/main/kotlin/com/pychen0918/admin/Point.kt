package com.pychen0918.admin

import kotlinx.serialization.Serializable

val pointStorage = mutableListOf<Point>(
    Point("cat1", "animal", 24.123512455, 35.12342345),
    Point("cat2", "animal", 24.123512455, 35.12342345)
)

@Serializable
data class Point(val id: String, val collection: String, val lat: Double, val lng: Double)