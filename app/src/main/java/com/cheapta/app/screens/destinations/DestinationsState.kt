package com.cheapta.app.screens.destinations

data class DestinationsState(
    val departureQuery: String = "",
    val departureLoading: Boolean = false,
    val departureLocations: List<Location> = emptyList(),
    val destinations: List<Destination> = emptyList(),
    val destinationQuery: String? = null,
    val destinationLoading: Boolean = false,
    val filteredDestinations: List<Destination> = emptyList(),
    val destinationLocations: List<Location> = emptyList(),
)