package com.cheapta.app.screens.destinations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheapta.app.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class DestinationsIntent {
    object GetLocation : DestinationsIntent()
    data class DepartureQuery(val query: String) : DestinationsIntent()
    data class LocationChange(val location: Location) : DestinationsIntent()
    data class DestinationQuery(val query: String) : DestinationsIntent()
}

data class DestinationsState(
    val location: Location? = null,
    val departureQuery: String = "",
    val departureLoading: Boolean = false,
    val departureLocations: List<Location> = emptyList(),
    val allDestinations: List<Destination> = emptyList(),
    val destinationQuery: String? = null,
    val destinationLoading: Boolean = false,
    val destinationsToShow: List<Destination> = emptyList(),
    val destinationLocations: List<Location> = emptyList(),
)

@HiltViewModel
class DestinationsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val intents = Channel<DestinationsIntent>()

    private val _state = MutableStateFlow(DestinationsState())
    val state: StateFlow<DestinationsState> = _state.asStateFlow()

    private val departureQueryFlow = Channel<String>()
    private val destinationQueryFlow = Channel<String>()

    init {

        departureQueryFlow.receiveAsFlow()
            .debounce(1000)
            .filter { it.isNotEmpty() }
            .onEach(::getDepartureLocations)
            .launchIn(viewModelScope)

        destinationQueryFlow.receiveAsFlow()
            .debounce(1000)
            .filter { it.isNotEmpty() }
            .onEach(::getDestinationLocations)
            .launchIn(viewModelScope)

        getDepartureLocation()

    }

    init {
        intents.receiveAsFlow()
            .onEach(::updateState)
            .launchIn(viewModelScope)
        intents.trySend(DestinationsIntent.GetLocation)
    }

    fun handleIntent(intent: DestinationsIntent) {
        intents.trySend(intent)
    }

    private fun updateState(intent: DestinationsIntent) {
        when (intent) {
            is DestinationsIntent.GetLocation -> getDepartureLocation()
            is DestinationsIntent.DepartureQuery -> onDepartureQueryChange(intent.query)
            is DestinationsIntent.LocationChange -> onLocationChange(intent.location)
            is DestinationsIntent.DestinationQuery -> onDestinationQueryChange(intent.query)
        }
    }

    private fun getDepartureLocation() {
        _state.update { it.copy(departureLoading = true) }
        viewModelScope.launch {
            val location = repository.getLocation()
            _state.update { it.copy(departureLoading = false) }
            onLocationChange(location)
        }
    }

    private fun getDestinations(location: Location) {
        _state.update { it.copy(destinationLoading = true) }
        viewModelScope.launch {
            val flyFrom = location.code
            flyFrom?.let {
                val destinations = repository.getDestinations(flyFrom = flyFrom)
                _state.update {
                    it.copy(
                        destinationLoading = false,
                        allDestinations = destinations,
                        destinationsToShow = destinations
                    )
                }
            }
        }
    }

    private fun onDepartureQueryChange(query: String) {
        _state.update { it.copy(departureQuery = query) }
        departureQueryFlow.trySend(query)
    }

    private fun getDepartureLocations(query: String) {
        _state.update { it.copy(departureLoading = true) }
        viewModelScope.launch {
            val locations = repository.getLocations(query)
            _state.update {
                it.copy(
                    departureLoading = false,
                    departureLocations = locations
                )
            }
        }
    }

    private fun onLocationChange(location: Location) {
        _state.update {
            it.copy(
                location = location,
                departureQuery = location.name ?: "",
                departureLocations = emptyList()
            )
        }
        getDestinations(location)
    }

    private fun onDestinationQueryChange(query: String) {
        _state.update { it.copy(
            destinationQuery = query,
            destinationLocations = emptyList()
        ) }
        val filteredDestinations =
            _state.value.allDestinations.filter { it.cityToName?.startsWith(query, true) == true }
        _state.update { it.copy(destinationsToShow = filteredDestinations) }
        if (filteredDestinations.isEmpty()) destinationQueryFlow.trySend(query)
    }

    private fun getDestinationLocations(query: String) {
        _state.update { it.copy(destinationLoading = true) }
        viewModelScope.launch {
            val locations = repository.getLocations(query)
            _state.update {
                it.copy(
                    destinationLoading = false,
                    destinationLocations = locations
                )
            }
        }
    }

}