package com.cheapta.app.screens.destinations

data class DestinationsState(
    val query: String = "",
    val locations: List<Location> = emptyList(),
    val destinations: List<Destination> = emptyList()
)