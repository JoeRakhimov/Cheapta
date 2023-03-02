package com.cheapta.app.screens.destinations

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
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
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun DestinationsScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        FromCityInput()
        DestinationsList()
    }
}

private val destinations = listOf(
    Destination("Tashkent"),
    Destination("Budapest"),
    Destination("Malta"),
    Destination("Lisbon")
)

@Composable
fun FromCityInput() {
    val textValue = rememberSaveable { mutableStateOf("Budapest") }
    Log.d("CheapTag", textValue.value)
    val primaryColor = colorResource(id = R.color.black)
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = stringResource(id = R.string.from)) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = primaryColor,
            focusedLabelColor = primaryColor,
            cursorColor = primaryColor
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Words
        ),
        value = textValue.value,
        onValueChange = {
            textValue.value = it
        }
    )
}

@Composable
fun DestinationsList() {
    LazyColumn {
        items(destinations) { item -> DestinationItem(item) }
    }
}

@Composable
fun DestinationItem(destination: Destination, modifier: Modifier = Modifier) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = destination.cityTo ?: "",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.black)
        )
    }
}