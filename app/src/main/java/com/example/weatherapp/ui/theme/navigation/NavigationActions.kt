package com.example.weatherapp.ui.theme.navigation

import androidx.navigation.NavController

class NavigationActions(navController: NavController) {
    val navigateToWeather: () -> Unit = {
        navController.navigate(NavigationDestination.WeatherScreen.route) {
            popUpTo(NavigationDestination.WeatherScreen.route)
        }
    }

    val navigateToFavorites: () -> Unit = {
        navController.navigate(NavigationDestination.FavoriteCitiesScreen.route) {
            launchSingleTop = true
        }
    }
}