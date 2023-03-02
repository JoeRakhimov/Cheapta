package com.cheapta.app.screens.destinations

data class DestinationsState(
    val loading: Boolean = false,
    val destinations: List<Destination> = emptyList()
)