package com.cheapta.app.screens.destinations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheapta.app.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DestinationsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DestinationsState())
    val uiState: StateFlow<DestinationsState> = _uiState.asStateFlow()

    private val queryDepartureFlow = Channel<String>()
    private val queryDestinationFlow = Channel<String>()

    init {
        _uiState.value = DestinationsState()

        queryDepartureFlow.receiveAsFlow()
            .debounce(1000)
            .filter { it.isNotEmpty() }
            .onEach(::getDepartureLocations)
            .launchIn(viewModelScope)

        queryDestinationFlow.receiveAsFlow()
            .debounce(1000)
            .filter { it.isNotEmpty() }
            .onEach(::getDestinationLocations)
            .launchIn(viewModelScope)

        getDepartureLocation()
    }

    private fun getDepartureLocation() {
        viewModelScope.launch {
            val location = repository.getLocation()
            _uiState.value = DestinationsState(
                queryDeparture = location.name ?: "",
            )
            getDestinations(location)
        }
    }

    fun onDepartureQueryChange(query: String) {
        _uiState.value = DestinationsState(queryDeparture = query)
        queryDepartureFlow.trySend(query)
    }

    private fun getDepartureLocations(query: String) {
        viewModelScope.launch {
            val locations = repository.getLocations(query)
            _uiState.value = DestinationsState(
                queryDeparture = uiState.value.queryDeparture,
                departureLocations = locations
            )
        }
    }

    fun onLocationChange(location: Location) {
        _uiState.value = DestinationsState(queryDeparture = location.name ?: "")
        getDestinations(location)
    }

    private fun getDestinations(location: Location) {
        viewModelScope.launch {
            val flyFrom = location.code
            flyFrom?.let {
                val destinations = repository.getDestinations(flyFrom = flyFrom)
                _uiState.value =
                    DestinationsState(
                        queryDeparture = location.name ?: "",
                        destinations = destinations,
                        filteredDestinations = destinations
                    )
            }
        }
    }

    fun onDestinationQueryChange(query: String) {
        val filteredDestinations =
            _uiState.value.destinations.filter { it.cityToName?.startsWith(query, true) == true }
        _uiState.value = DestinationsState(
            queryDeparture = _uiState.value.queryDeparture,
            destinations = _uiState.value.destinations,
            queryDestination = query,
            filteredDestinations = filteredDestinations
        )
        if (filteredDestinations.isEmpty()) {
            queryDestinationFlow.trySend(query)
        }
    }

    private fun getDestinationLocations(query: String) {
        viewModelScope.launch {
            val locations = repository.getLocations(query)
            _uiState.value = DestinationsState(
                queryDeparture = uiState.value.queryDeparture,
                destinations = uiState.value.destinations,
                queryDestination = uiState.value.queryDestination,
                destinationLocations = locations
            )
        }
    }

}