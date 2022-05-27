package com.example.weatherappcompose.datasource

import com.example.weatherappcompose.datasource.networking.models.WeatherApiResponse
import com.example.weatherappcompose.home.WeatherUiModel

sealed class WeatherUiState{
    object Empty:WeatherUiState()
    object Loading:WeatherUiState()
    class Loaded(val data:WeatherUiModel):WeatherUiState()
    class Error(val message:String):WeatherUiState()
}
