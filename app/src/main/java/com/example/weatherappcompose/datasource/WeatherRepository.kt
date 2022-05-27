package com.example.weatherappcompose.datasource

import com.example.weatherappcompose.datasource.networking.ApiService
import com.example.weatherappcompose.datasource.networking.models.WeatherApiResponse
import javax.inject.Inject

class WeatherRepository @Inject constructor ( private val apiService: ApiService) {
   suspend fun getWeatherData(lat:String,long:String):WeatherApiResponse =
        apiService.fetchWeather(lat,long)
}