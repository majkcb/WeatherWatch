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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.connection.Main
import com.example.weatherapp.connection.Sys
import com.example.weatherapp.connection.Weather
import com.example.weatherapp.connection.WeatherResponse
import com.example.weatherapp.ui.theme.screens.viewmodels.FavoriteCityViewModel
import com.example.weatherapp.ui.theme.screens.viewmodels.WeatherUiState
import com.example.weatherapp.ui.theme.screens.viewmodels.WeatherViewModel

@Composable
fun WeatherScreen(
    weatherViewModel: WeatherViewModel, favoriteCityViewModel: FavoriteCityViewModel
) {
    val state by weatherViewModel.uiState.collectAsState()

    WeatherScreenContent(cityInput = state.cityInput,
        onCityInputChange = { weatherViewModel.updateCityInput(it) },
        onFetchWeather = { weatherViewModel.fetchWeather(state.cityInput) },
        weatherState = state,
        onAddToFavorites = { favoriteCityViewModel.addFavoriteCity(it) })
}

@Composable
fun WeatherScreenContent(
    cityInput: String,
    onCityInputChange: (String) -> Unit,
    onFetchWeather: () -> Unit,
    weatherState: WeatherUiState,
    onAddToFavorites: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = cityInput,
            onValueChange = onCityInputChange,
            label = { Text(stringResource(R.string.enter_city_name)) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedBorderColor = Color.DarkGray,
                focusedLabelColor = Color.DarkGray,
                cursorColor = Color.DarkGray
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onFetchWeather, colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black, contentColor = Color.White
            )
        ) {
            if (weatherState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(R.string.fetching_weather))
            } else {
                Text(stringResource(R.string.fetch_weather))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        weatherState.weatherResponse?.let {
            Text(stringResource(R.string.city, it.name, it.sys.country))
            Text(stringResource(R.string.temperature_c, it.main.temp))
            Text(stringResource(R.string.min_temp_c, it.main.tempMin))
            Text(stringResource(R.string.max_temp_c, it.main.tempMax))
            Text(stringResource(R.string.humidity, it.main.humidity))
            Text(stringResource(R.string.pressure_hpa, it.main.pressure))
            Text(stringResource(R.string.description, it.weather[0].description))
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { onAddToFavorites(it.name) }) {
                Text(stringResource(R.string.add_to_favorites))
            }
        }
    }
    weatherState.errorMessage?.let {
        Text(text = "Error: $it", color = Color.Red)
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherScreenPreview() {
    WeatherScreenContent(cityInput = "Stockholm",
        onCityInputChange = {},
        onFetchWeather = {},
        weatherState = WeatherUiState(
            isLoading = false, weatherResponse = WeatherResponse(
                name = "Stockholm",
                sys = Sys(country = "SE"),
                main = Main(
                    temp = 18.0,
                    tempMin = 15.0,
                    tempMax = 20.0,
                    humidity = 65,
                    pressure = 1012,
                    feelsLike = 17.0
                ),
                weather = listOf(Weather(description = "clear sky", icon = "01d")),
                dt = 1625234400,
                timezone = 7200
            )
        ),
        onAddToFavorites = {})
}


