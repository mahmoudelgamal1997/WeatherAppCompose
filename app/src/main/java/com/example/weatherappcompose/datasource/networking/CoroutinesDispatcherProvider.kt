package com.example.weatherappcompose.datasource.networking

import kotlinx.coroutines.Dispatchers

class CoroutinesDispatcherProvider {
    fun IO() = Dispatchers.IO
    fun Default() = Dispatchers.Default
    fun Main() = Dispatchers.Main
}