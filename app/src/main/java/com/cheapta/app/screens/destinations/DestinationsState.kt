package com.cheapta.app.screens.destinations

data class DestinationsState(
    val queryDeparture: String = "",
    val departureLocations: List<Location> = emptyList(),
    val destinations: List<Destination> = emptyList(),
    val queryDestination: String? = null,
    val filteredDestinations: List<Destination> = emptyList(),
    val destinationLocations: List<Location> = emptyList(),
)