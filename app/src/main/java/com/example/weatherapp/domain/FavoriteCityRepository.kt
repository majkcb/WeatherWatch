package com.example.weatherapp.domain

import com.example.weatherapp.data.database.FavoriteCity
import com.example.weatherapp.data.database.FavoriteCityDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteCityRepository @Inject constructor(
    private val favoriteCityDao: FavoriteCityDao
) {
    suspend fun addFavoriteCity(cityName: String) {
        favoriteCityDao.insert(FavoriteCity(cityName))
    }

    fun getFavoriteCities(): Flow<List<FavoriteCity>> {
        return favoriteCityDao.getFavoriteCities()
    }
}