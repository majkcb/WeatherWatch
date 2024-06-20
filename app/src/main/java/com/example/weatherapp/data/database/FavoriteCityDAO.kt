package com.example.weatherapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteCity: FavoriteCity)

    @Query("SELECT * FROM favorites")
    fun getFavoriteCities(): Flow<List<FavoriteCity>>

    @Query("DELETE FROM favorites WHERE cityName = :cityName")
    suspend fun deleteFavoriteCity(cityName: String)
}