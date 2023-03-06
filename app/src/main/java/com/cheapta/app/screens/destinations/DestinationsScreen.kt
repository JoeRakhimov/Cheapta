package com.cheapta.app.screens.destinations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cheapta.app.R
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DestinationsScreen(
    viewModel: DestinationsViewModel = viewModel()
) {

    val destinationsState by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
    ) {

        CitySearchInput(
            label = stringResource(id = R.string.from),
            query = destinationsState.departureQuery,
            inProgress = destinationsState.departureLoading,
            onQueryChange = { query ->
                viewModel.handleIntent(DestinationsIntent.DepartureQuery(query))
            })

        LocationsList(destinationsState.departureLocations, onLocationSelected = { location ->
            viewModel.handleIntent(DestinationsIntent.LocationChange(location))
        })

        CitySearchInput(
            label = stringResource(id = R.string.to),
            query = destinationsState.destinationQuery ?: stringResource(id = R.string.everywhere),
            inProgress = destinationsState.destinationLoading,
            onQueryChange = { query ->
                viewModel.handleIntent(DestinationsIntent.DestinationQuery(query))
            })

        LocationsList(destinationsState.destinationLocations, onLocationSelected = { location ->

        })

        DestinationsList(destinationsState.destinationsToShow)


    }

}

@Composable
fun CitySearchInput(
    label: String,
    query: String,
    inProgress: Boolean,
    onQueryChange: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val primaryColor = colorResource(id = R.color.black)
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .focusRequester(focusRequester),
        label = { Text(text = label) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = primaryColor,
            focusedLabelColor = primaryColor,
            cursorColor = primaryColor
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Words
        ),
        value = query,
        onValueChange = onQueryChange,
        trailingIcon = {
            if(inProgress){
                CircularProgressIndicator(
                    modifier = Modifier.then(Modifier.size(24.dp)),
                    color = colorResource(
                        id = R.color.black
                    )
                )
            } else if (query.isNotEmpty()) {
                Icon(
                    Icons.Default.Clear,
                    contentDescription = "Clear",
                    modifier = Modifier
                        .clickable {
                            onQueryChange("")
                            focusRequester.requestFocus()
                        }
                )
            }
        }
    )
}

@Composable
fun LocationsList(locations: List<Location>, onLocationSelected: (Location) -> Unit) {
    if (locations.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 12.dp)
        ) {
            items(locations) { item -> LocationItem(item, onLocationSelected) }
        }
    }
}

@Composable
fun LocationItem(location: Location, onLocationSelected: (Location) -> Unit) {
    val focusManager = LocalFocusManager.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                focusManager.clearFocus()
                onLocationSelected(location)
            }
    ) {
        Text(
            modifier = Modifier
                .wrapContentWidth()
                .padding(end = 8.dp),
            text = location.flag ?: "",
            fontSize = 22.sp
        )
        Text(
            text = location.name ?: "",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.black)
        )
    }
}

@Composable
fun DestinationsList(destinations: List<Destination>) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(destinations) { item -> DestinationItem(item) }
    }
}

@Composable
fun DestinationItem(destination: Destination) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 8.dp)
    ) {
        Text(
            modifier = Modifier
                .wrapContentWidth()
                .padding(end = 8.dp),
            text = destination.countryToFlag ?: "",
            fontSize = 22.sp
        )
        Text(
            text = destination.cityToName ?: "",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.black)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            text = destination.price.toString(),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.black)
        )
    }
}