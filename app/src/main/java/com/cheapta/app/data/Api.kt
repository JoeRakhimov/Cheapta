package com.cheapta.app.data

import com.cheapta.app.screens.destinations.Destination
import com.cheapta.app.screens.destinations.Location
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "http://api.joerakhimov.com/cheapta/"

const val DESTINATIONS_DEFAULT_FLIGHT_TYPE = "oneway"
const val DESTINATIONS_DEFAULT_MAX_STOPS = 0

interface Api {

    @GET("location")
    suspend fun getLocation(): Location

    @GET("query")
    suspend fun query(
        @Query("term") term: String,
        @Query("location_types") location_types: String = "city"
    ): List<Location>

    @GET("destinations")
    suspend fun getDestinations(
        @Query("fly_from") flyFrom: String? = null,
        @Query("flight_type") flightType: String? = DESTINATIONS_DEFAULT_FLIGHT_TYPE,
        @Query("max_stops") maxStops: Int? = DESTINATIONS_DEFAULT_MAX_STOPS
    ): List<Destination>

}