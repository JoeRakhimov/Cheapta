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
        _uiState.value = DestinationsState(
            departureLoading = true,
        )
        viewModelScope.launch {
            val location = repository.getLocation()
            _uiState.value = DestinationsState(
                departureQuery = location.name ?: "",
            )
            getDestinations(location)
        }
    }

    fun onDepartureQueryChange(query: String) {
        _uiState.value = DestinationsState(
            departureQuery = query,
            destinationQuery = uiState.value.destinationQuery,
            departureLoading = uiState.value.departureLoading,
            destinationLoading = uiState.value.destinationLoading
        )
        queryDepartureFlow.trySend(query)
    }

    private fun getDepartureLocations(query: String) {
        viewModelScope.launch {
            val locations = repository.getLocations(query)
            _uiState.value = DestinationsState(
                departureQuery = uiState.value.departureQuery,
                destinationQuery = uiState.value.destinationQuery,
                departureLocations = locations
            )
        }
    }

    fun onLocationChange(location: Location) {
        _uiState.value = DestinationsState(departureQuery = location.name ?: "")
        getDestinations(location)
    }

    private fun getDestinations(location: Location) {
        _uiState.value = DestinationsState(
            departureQuery = uiState.value.departureQuery,
            destinationLoading = true,
            destinationQuery = uiState.value.destinationQuery,
        )
        viewModelScope.launch {
            val flyFrom = location.code
            flyFrom?.let {
                val destinations = repository.getDestinations(flyFrom = flyFrom)
                _uiState.value =
                    DestinationsState(
                        departureQuery = location.name ?: "",
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
            departureQuery = _uiState.value.departureQuery,
            destinations = _uiState.value.destinations,
            destinationQuery = query,
            filteredDestinations = filteredDestinations
        )
        if (filteredDestinations.isEmpty()) {
            queryDestinationFlow.trySend(query)
        }
    }

    private fun getDestinationLocations(query: String) {
        _uiState.value = DestinationsState(
            destinationLoading = true,
            departureQuery = uiState.value.departureQuery,
            destinationQuery = uiState.value.destinationQuery,
        )
        viewModelScope.launch {
            val locations = repository.getLocations(query)
            _uiState.value = DestinationsState(
                departureQuery = uiState.value.departureQuery,
                destinations = uiState.value.destinations,
                destinationQuery = uiState.value.destinationQuery,
                destinationLocations = locations
            )
        }
    }

}