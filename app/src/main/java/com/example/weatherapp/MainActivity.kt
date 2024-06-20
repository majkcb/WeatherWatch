package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.ui.theme.navigation.NavigationActions
import com.example.weatherapp.ui.theme.navigation.NavigationDestination
import com.example.weatherapp.ui.theme.screens.FavoriteCitiesScreen
import com.example.weatherapp.ui.theme.screens.WeatherScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                val navController = rememberNavController()
                val navActions = remember(navController) { NavigationActions(navController) }

                Scaffold(bottomBar = {
                    BottomNavigationBar(navController)
                }) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = NavigationDestination.WeatherScreen.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(NavigationDestination.WeatherScreen.route) {
                            WeatherScreen(
                                weatherViewModel = hiltViewModel(),
                                favoriteCityViewModel = hiltViewModel()
                            )
                        }
                        composable(NavigationDestination.FavoriteCitiesScreen.route) {
                            FavoriteCitiesScreen(
                                onNavigateBack = navActions.navigateToWeather,
                                favoriteCityViewModel = hiltViewModel()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationDestination.WeatherScreen, NavigationDestination.FavoriteCitiesScreen
    )
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { screen ->
            NavigationBarItem(icon = {
                Icon(
                    painter = painterResource(id = if (screen is NavigationDestination.WeatherScreen) R.drawable.ic_home else R.drawable.ic_favorite),
                    contentDescription = null
                )
            },
                label = { Text(screen.route) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationRoute!!) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    }
}
