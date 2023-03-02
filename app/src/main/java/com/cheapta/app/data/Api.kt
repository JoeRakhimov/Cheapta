package com.cheapta.app.data

import com.cheapta.app.screens.destinations.Destination
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "http://api.joerakhimov.com/cheapta/"

interface Api {

    @GET("destinations")
    suspend fun getDestinations(@Query("fly_from") flyFrom: String? = null, @Query("flight_type") flightType: String? = null, @Query("max_stops") maxStops: Int? = null): List<Destination>

}