package com.cheapta.app.screens.destinations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheapta.app.data.Api
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import retrofit2.http.GET
import javax.inject.Inject

@HiltViewModel
class DestinationsViewModel @Inject constructor(
    private val api: Api
): ViewModel() {

    private val _destinations = MutableLiveData<List<Destination>>()
    val destinations: LiveData<List<Destination>> = _destinations

    fun getDestinations() {
        viewModelScope.launch {
            _destinations.value = api.getDestinations()
        }
    }

}