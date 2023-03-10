package com.cheapta.app.screens.destinations

import androidx.lifecycle.Observer
import com.cheapta.app.repository.Repository
import com.cheapta.app.screens.MainDispatcherRule
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class DestinationsViewModelTest{

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    lateinit var repository: Repository

    lateinit var viewModel: DestinationsViewModel

    @Mock
    lateinit var intentObserver: Observer<DestinationsIntent>

    @Before
    fun setup() {
        viewModel = DestinationsViewModel(repository)
    }

    @Test
    fun getDepartureLocation_shouldShowDepartureLoading(){
        viewModel.intents.trySend(DestinationsIntent.GetLocation)
        Assert.assertTrue(viewModel.state.value.departureLoading)
    }

}