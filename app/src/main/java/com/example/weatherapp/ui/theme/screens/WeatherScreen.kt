package com.example.weatherapp.ui.theme.screens

import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.data.connection.Main
import com.example.weatherapp.data.connection.Sys
import com.example.weatherapp.data.connection.Weather
import com.example.weatherapp.data.connection.WeatherResponse

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
    val context = LocalContext.current
    var showToast by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(false) }

    if (showToast) {
        val toast = Toast.makeText(context, stringResource(R.string.added_to_favorites), Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP, 0, 100)
        toast.show()
        showToast = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(value = cityInput,
            onValueChange = onCityInputChange,
            label = { Text(stringResource(R.string.enter_city_name)) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedBorderColor = Color.DarkGray,
                focusedLabelColor = Color.DarkGray,
                cursorColor = Color.DarkGray
            ),
            trailingIcon = {
                IconButton(onClick = onFetchWeather) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(R.string.fetch_weather),
                        tint = Color.DarkGray
                    )
                }
            })
        Spacer(modifier = Modifier.height(16.dp))
        weatherState.weatherResponse?.let {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFBBDEFB))
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.city, it.name, it.sys.country),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.temperature_c, it.main.temp),
                        fontSize = 20.sp
                    )
                    Text(
                        text = stringResource(R.string.min_temp_c, it.main.tempMin),
                        fontSize = 20.sp
                    )
                    Text(
                        text = stringResource(R.string.max_temp_c, it.main.tempMax),
                        fontSize = 20.sp
                    )
                    Text(
                        text = stringResource(R.string.humidity, it.main.humidity), fontSize = 20.sp
                    )
                    Text(
                        text = stringResource(R.string.pressure_hpa, it.main.pressure),
                        fontSize = 20.sp
                    )
                    Text(
                        text = stringResource(R.string.description, it.weather[0].description),
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    IconButton(onClick = {
                        onAddToFavorites(it.name)
                        showToast = true
                        isFavorite = !isFavorite
                    }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = stringResource(R.string.add_to_favorites),
                            tint = Color.Red,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
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
