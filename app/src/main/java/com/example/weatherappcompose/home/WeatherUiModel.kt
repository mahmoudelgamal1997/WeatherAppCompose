package com.example.weatherappcompose.home

data class WeatherUiModel(
    var city:String="",
    var temprature:String="",
    var forecastForWeek: List<ForecastPerDay> = listOf())

data class ForecastPerDay(
var day:String,
var dayWeather:String,
var nightWeather:String)