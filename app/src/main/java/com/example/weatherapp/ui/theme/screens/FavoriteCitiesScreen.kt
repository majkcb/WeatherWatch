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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.weatherapp.data.models.FavoriteCity
import com.example.weatherapp.ui.theme.screens.viewmodels.FavoriteCityViewModel

@Composable
fun FavoriteCitiesScreen(
    favoriteCityViewModel: FavoriteCityViewModel, onNavigateBack: () -> Unit
) {
    val favoriteCities by favoriteCityViewModel.favoriteCities.collectAsState()

    FavoriteCitiesScreenContent(
        favoriteCities = favoriteCities,
        onDeleteFavoriteCity = { favoriteCityViewModel.deleteFavoriteCity(it) },
        onNavigateBack = onNavigateBack
    )
}

@Composable
fun FavoriteCitiesScreenContent(
    favoriteCities: List<FavoriteCity>,
    onDeleteFavoriteCity: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    var showToast by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(false) }

    if (showToast) {
        val toast = Toast.makeText(context, stringResource(R.string.removed_from_favorites), Toast.LENGTH_SHORT)
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
        Text(text = stringResource(R.string.favorite_cities), fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        favoriteCities.forEach { city ->
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
                        text = city.cityName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    IconButton(onClick = {
                        onDeleteFavoriteCity(city.cityName)
                        showToast = true
                        isFavorite = !isFavorite
                    }) {
                        Icon(
                            imageVector = if (!isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = stringResource(R.string.removed_from_favorites),
                            tint = Color.Red,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onNavigateBack,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            )
        ) {
            Text(stringResource(R.string.back_to_weather))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteCitiesScreenPreview() {
    FavoriteCitiesScreenContent(
        favoriteCities = listOf(
            FavoriteCity(cityName = "Stockholm"),
            FavoriteCity(cityName = "London"),
            FavoriteCity(cityName = "New York")
        ),
        onDeleteFavoriteCity = {},
        onNavigateBack = {}
    )
}
