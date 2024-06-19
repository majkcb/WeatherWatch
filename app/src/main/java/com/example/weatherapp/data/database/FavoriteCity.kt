package com.example.weatherapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteCity(
    @PrimaryKey val cityName: String
)
