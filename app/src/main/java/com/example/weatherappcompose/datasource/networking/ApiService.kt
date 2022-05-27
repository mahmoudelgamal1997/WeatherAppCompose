package com.example.weatherappcompose.datasource.networking

import com.example.weatherappcompose.datasource.networking.models.WeatherApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("data/2.5/onecall?exclude=minutely,hourly,alerts&lang=en&units=imperial")
    suspend fun fetchWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String = "4596df18d7f3e4389a40371e52e0ef9c"
    ): WeatherApiResponse

}