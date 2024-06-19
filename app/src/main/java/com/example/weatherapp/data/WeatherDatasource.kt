package com.example.weatherapp.data

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.weatherapp.connection.ApiService
import com.example.weatherapp.connection.WeatherResponse
import com.google.gson.Gson
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

interface WeatherDataSource {
    suspend fun getWeather(city: String): Either<WeatherError, WeatherResponse>
}

@Singleton
class WeatherDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : WeatherDataSource {

    override suspend fun getWeather(city: String): Either<WeatherError, WeatherResponse> {
        val response = apiService.getWeather(city, "201c92da77977ac5e894c0ef73adf95f")

        return handleResponse(response)
    }

    private fun handleResponse(response: Response<WeatherResponse>): Either<WeatherError, WeatherResponse> {
        return if (response.isSuccessful) {
            response.body()?.right() ?: WeatherError.WeatherNotFound.left()
        } else {
            response.errorBody()?.let {
                val error = try {
                    Gson().fromJson(it.string(), WeatherError::class.java)
                } catch (e: Exception) {
                    WeatherError.ServerError
                }
                error.left()
            } ?: WeatherError.ServerError.left()
        }
    }

}

sealed class WeatherError {
    data object NetworkError : WeatherError()
    data object WeatherNotFound : WeatherError()
    data object ServerError : WeatherError()
}
