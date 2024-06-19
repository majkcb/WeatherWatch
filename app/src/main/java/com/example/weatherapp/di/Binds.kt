package com.example.weatherapp.di

import com.example.weatherapp.data.WeatherDataSource
import com.example.weatherapp.data.WeatherDataSourceImpl
import com.example.weatherapp.domain.WeatherRepository
import com.example.weatherapp.domain.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ProjectWideDI {

    @Binds
    abstract fun bindWeatherDataSource(
        dataSourceImpl: WeatherDataSourceImpl
    ): WeatherDataSource

    @Binds
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository
}