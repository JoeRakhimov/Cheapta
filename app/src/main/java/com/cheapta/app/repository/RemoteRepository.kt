package com.cheapta.app.repository

import com.cheapta.app.data.Api

class RemoteRepository(private val api: Api): Repository {

    override suspend fun getLocation() = api.getLocation()

    override suspend fun getLocations(query: String) = api.query(term=query)

    override suspend fun getDestinations(flyFrom: String) = api.getDestinations(flyFrom)

}