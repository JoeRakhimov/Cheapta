package com.cheapta.app.repository

import com.cheapta.app.screens.destinations.Destination
import com.cheapta.app.screens.destinations.Location
import com.cheapta.app.data.Result

interface Repository {

    suspend fun getLocation(): Result<Location>
    suspend fun getLocations(query: String): Result<List<Location>>
    suspend fun getDestinations(flyFrom: String): Result<List<Destination>>

}