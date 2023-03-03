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

    private val queryFlow = Channel<String>()

    init {
        _uiState.value = DestinationsState()
        queryFlow.receiveAsFlow()
            .debounce(1000)
            .filter { it.isNotEmpty() }
            .onEach(::getLocations)
            .launchIn(viewModelScope)
        getLocation()
    }

    private fun getLocation() {
        viewModelScope.launch {
            val location = repository.getLocation()
            _uiState.value = DestinationsState(query = location.name ?: "")
            getDestinations(location)
        }
    }

    fun onQueryChange(query: String) {
        _uiState.value = DestinationsState(query = query)
        queryFlow.trySend(query)
    }

    private fun getLocations(query: String) {
        viewModelScope.launch {
            val locations = repository.getLocations(query)
            _uiState.value = DestinationsState(
                query = uiState.value.query,
                locations = locations
            )
        }
    }

    fun onLocationChange(location: Location) {
        _uiState.value = DestinationsState(query = location.name ?: "")
        getDestinations(location)
    }

    private fun getDestinations(location: Location) {
        viewModelScope.launch {
            val flyFrom = location.code
            flyFrom?.let {
                val destinations = repository.getDestinations(flyFrom = flyFrom)
                _uiState.value =
                    DestinationsState(query = location.name ?: "", destinations = destinations)
            }
        }
    }

}