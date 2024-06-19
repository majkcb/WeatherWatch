package com.example.weatherapp.ui.theme.viewmodels

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.R
import com.example.weatherapp.connection.WeatherResponse
import com.example.weatherapp.data.WeatherError
import com.example.weatherapp.domain.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    fun fetchWeather(city: String) {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getWeather(city).fold(ifLeft = { error ->
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = mapErrorToMessageId(error))
                }
            }, ifRight = { weatherResponse ->
                _uiState.update {
                    it.copy(
                        isLoading = false, weatherResponse = weatherResponse, errorMessage = null
                    )
                }
            })
        }
    }

    private fun mapErrorToMessageId(error: WeatherError): Int {
        return when (error) {
            WeatherError.NetworkError -> R.string.network_error
            WeatherError.WeatherNotFound -> R.string.weather_not_found
            WeatherError.ServerError -> R.string.server_error
        }
    }
}

data class WeatherUiState(
    val isLoading: Boolean = false,
    val weatherResponse: WeatherResponse? = null,
    @StringRes val errorMessage: Int? = null
)
