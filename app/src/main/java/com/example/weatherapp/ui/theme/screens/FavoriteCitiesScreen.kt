package com.example.weatherapp.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.screens.viewmodels.FavoriteCityViewModel

@Composable
fun FavoriteCitiesScreen(
    favoriteCityViewModel: FavoriteCityViewModel, onNavigateBack: () -> Unit
) {
    val favoriteCities by favoriteCityViewModel.favoriteCities.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(stringResource(R.string.favorite_cities))
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn {
            items(favoriteCities) { city ->
                Text(text = city.cityName)
                Button(
                    onClick = { favoriteCityViewModel.deleteFavoriteCity(city.cityName) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red, contentColor = Color.White
                    )
                ) {
                    Text(text = stringResource(R.string.remove))
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNavigateBack) {
            Text(stringResource(R.string.back_to_weather))
        }
    }
}