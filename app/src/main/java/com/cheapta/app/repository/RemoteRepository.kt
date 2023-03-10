package com.cheapta.app.repository

import com.cheapta.app.data.Api
import com.cheapta.app.data.Result
import com.cheapta.app.screens.destinations.Destination
import com.cheapta.app.screens.destinations.Location

class RemoteRepository(private val api: Api): Repository {

    override suspend fun getLocation(): Result<Location>{
        return try {
            Result.Success(api.getLocation())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getLocations(query: String): Result<List<Location>>{
        return try {
            Result.Success(api.query(term=query))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getDestinations(flyFrom: String): Result<List<Destination>>{
        return try {
            Result.Success(api.getDestinations(flyFrom))
        } catch (e: Exception){
            Result.Error(e)
        }
    }

}