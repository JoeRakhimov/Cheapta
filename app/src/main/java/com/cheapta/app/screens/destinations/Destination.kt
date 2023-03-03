package com.cheapta.app.screens.destinations

import com.google.gson.annotations.SerializedName

data class Destination(

    @field:SerializedName("city_to_name")
    val cityToName: String? = null,

    @field:SerializedName("distance")
    val distance: Double? = null,

    @field:SerializedName("departure_date")
    val departureDate: String? = null,

    @field:SerializedName("fly_from")
    val flyFrom: String? = null,

    @field:SerializedName("source")
    val source: String? = null,

    @field:SerializedName("city_from_name")
    val cityFromName: String? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("city_from")
    val cityFrom: String? = null,

    @field:SerializedName("price")
    val price: Double? = null,

    @field:SerializedName("flight_type")
    val flightType: String? = null,

    @field:SerializedName("airlines")
    val airlines: List<String?>? = null,

    @field:SerializedName("currency")
    val currency: String? = null,

    @field:SerializedName("country_from")
    val countryFrom: String? = null,

    @field:SerializedName("country_from_flag")
    val countryFromFlag: String? = null,

    @field:SerializedName("stops")
    val stops: Int? = null,

    @field:SerializedName("country_to")
    val countryTo: String? = null,

    @field:SerializedName("country_to_flag")
    val countryToFlag: String? = null,

    @field:SerializedName("fly_to")
    val flyTo: String? = null,

    @field:SerializedName("city_to")
    val cityTo: String? = null,

    @field:SerializedName("ratio")
    val ratio: Double? = null,

    @field:SerializedName("discount")
    val discount: Double? = null,

)
