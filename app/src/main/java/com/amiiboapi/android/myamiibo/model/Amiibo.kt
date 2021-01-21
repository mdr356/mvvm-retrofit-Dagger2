package com.amiiboapi.android.myamiibo.model

data class Amiibo(
    val amiibo: List<AmiiboData>
)

data class AmiiboData(
    val amiiboSeries: String,
    val character: String,
    val gameSeries: String,
    val head: String,
    val image: String,
    val name: String,
    val release : AmiiboRelease,
    val tail: String,
    val type: String,
)

data class AmiiboRelease(
    val au: String,
    val eu: String,
    val jp: String,
    val na: String,
)