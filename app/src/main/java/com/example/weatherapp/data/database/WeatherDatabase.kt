package com.example.weatherapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.data.models.FavoriteCity

@Database(entities = [FavoriteCity::class], version = 1, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun favoriteCityDao(): FavoriteCityDao
}