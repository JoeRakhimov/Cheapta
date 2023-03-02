package com.cheapta.app.screens.destinations

data class Destination(
    val cityToName: String? = null,
    val distance: Double? = null,
    val departureDate: String? = null,
    val flyFrom: String? = null,
    val source: String? = null,
    val cityFromName: String? = null,
    val url: String? = null,
    val cityFrom: String? = null,
    val price: Int? = null,
    val flightType: String? = null,
    val airlines: List<String?>? = null,
    val currency: String? = null,
    val countryFrom: String? = null,
    val stops: Int? = null,
    val countryTo: String? = null,
    val flyTo: String? = null,
    val cityTo: String? = null,
    val ratio: Double? = null,
    val discount: Double? = null
)
