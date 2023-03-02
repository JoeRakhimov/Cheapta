package com.cheapta.app.screens.destinations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheapta.app.data.Api
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DestinationsViewModel @Inject constructor(
    private val api: Api
) : ViewModel() {

    private val _uiState = MutableStateFlow(DestinationsState())
    val uiState: StateFlow<DestinationsState> = _uiState.asStateFlow()

    init {
        getDestinations()
    }

    private fun getDestinations() {
        _uiState.value = DestinationsState(loading = true)
        viewModelScope.launch {
            val flyFrom = "BUD"
            val flightType = "oneway"
            val maxStops = 0
            val destinations = api.getDestinations(flyFrom = flyFrom, flightType = flightType, maxStops = maxStops)
            _uiState.value = DestinationsState(loading = false, destinations = destinations)
        }
    }

}