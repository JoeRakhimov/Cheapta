package com.cheapta.app.repository

import com.cheapta.app.screens.destinations.Destination
import com.cheapta.app.screens.destinations.Location

interface Repository {

    suspend fun getLocation(): Location
    suspend fun getLocations(query: String): List<Location>
    suspend fun getDestinations(flyFrom: String): List<Destination>

}