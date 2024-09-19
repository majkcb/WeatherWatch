package com.example.weatherapp.domain

import arrow.core.Either
import com.example.weatherapp.data.connection.WeatherResponse
import com.example.weatherapp.data.WeatherDataSource
import com.example.weatherapp.data.WeatherError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface WeatherRepository {
    suspend fun getWeather(city: String): Either<WeatherError, WeatherResponse>
}

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val dataSource: WeatherDataSource
) : WeatherRepository {

    override suspend fun getWeather(city: String): Either<WeatherError, WeatherResponse> =
        withContext(Dispatchers.IO) {
            dataSource.getWeather(city)
        }
}
