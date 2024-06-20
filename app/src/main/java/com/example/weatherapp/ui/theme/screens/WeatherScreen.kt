package com.example.weatherapp.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.viewmodels.FavoriteCityViewModel
import com.example.weatherapp.ui.theme.viewmodels.WeatherViewModel

@Composable
fun WeatherScreen(
    weatherViewModel: WeatherViewModel,
    favoriteCityViewModel: FavoriteCityViewModel,
    onNavigateToFavorites: () -> Unit
) {
    val state by weatherViewModel.uiState.collectAsState()

    var city by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text(stringResource(R.string.enter_city_name)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { weatherViewModel.fetchWeather(city) }) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(R.string.fetching_weather))
            } else {
                Text(stringResource(R.string.fetch_weather))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        state.weatherResponse?.let {
            Text(stringResource(R.string.city, it.name, it.sys.country))
            Text(stringResource(R.string.temperature_c, it.main.temp))
            Text(stringResource(R.string.min_temp_c, it.main.tempMin))
            Text(stringResource(R.string.max_temp_c, it.main.tempMax))
            Text(stringResource(R.string.humidity, it.main.humidity))
            Text(stringResource(R.string.pressure_hpa, it.main.pressure))
            Text(stringResource(R.string.description, it.weather[0].description))
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { favoriteCityViewModel.addFavoriteCity(it.name) }) {
                Text("Add to Favorites")
            }
        }
    }
    state.errorMessage?.let {
        Text(text = "Error: $it", color = Color.Red)
    }
}
