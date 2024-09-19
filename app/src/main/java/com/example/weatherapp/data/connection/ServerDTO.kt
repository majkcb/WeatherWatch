package com.example.weatherapp.data.connection

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val main: Main,
    val weather: List<Weather>,
    val name: String,
    val sys: Sys,
    val dt: Long,
    val timezone: Int
)

data class Main(
    val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double,
    val pressure: Int,
    val humidity: Int
)

data class Weather(
    val description: String,
    val icon: String
)

data class Sys(
    val country: String
)

