package com.cheapta.app.data

import com.cheapta.app.screens.destinations.Destination
import retrofit2.http.GET

const val BASE_URL = "http://api.joerakhimov.com/cheapta/"

interface Api {

    @GET("destinations")
    suspend fun getDestinations(): List<Destination>

}