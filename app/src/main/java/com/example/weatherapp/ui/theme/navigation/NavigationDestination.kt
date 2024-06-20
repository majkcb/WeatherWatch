package com.example.weatherapp.ui.theme.navigation

sealed class NavigationDestination(val route: String) {
    data object WeatherScreen : NavigationDestination("weatherScreen")
    data object FavoriteCitiesScreen : NavigationDestination("favoriteCitiesScreen")
}