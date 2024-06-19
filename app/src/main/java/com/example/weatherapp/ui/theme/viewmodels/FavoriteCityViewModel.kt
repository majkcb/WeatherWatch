package com.example.weatherapp.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.database.FavoriteCity
import com.example.weatherapp.domain.FavoriteCityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteCityViewModel @Inject constructor(
    private val favoriteCityRepository: FavoriteCityRepository
) : ViewModel() {

    private val _favoriteCities = MutableStateFlow<List<FavoriteCity>>(emptyList())
    val favoriteCities: StateFlow<List<FavoriteCity>> = _favoriteCities.asStateFlow()

    init {
        viewModelScope.launch {
            favoriteCityRepository.getFavoriteCities().collect { cities ->
                _favoriteCities.value = cities
            }
        }
    }

    fun addFavoriteCity(cityName: String) {
        viewModelScope.launch {
            favoriteCityRepository.addFavoriteCity(cityName)
        }
    }
}