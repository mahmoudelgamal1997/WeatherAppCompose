package com.example.weatherappcompose.home

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappcompose.datasource.WeatherRepository
import com.example.weatherappcompose.datasource.WeatherUiState
import com.example.weatherappcompose.datasource.networking.CoroutinesDispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository,
  //  @ApplicationContext private val context: ApplicationContext,
    private val coroutinesDispatcherProvider: CoroutinesDispatcherProvider
    ) :ViewModel() {

    private var city: String = "Austin, TX"
    private var _uiState= MutableStateFlow<WeatherUiState>(WeatherUiState.Empty)
    val uiState:StateFlow<WeatherUiState>
    get() = _uiState

    init {
        fetchWeather()
    }


    private fun fetchWeather(){
        _uiState.value = WeatherUiState.Loading
        viewModelScope.launch(coroutinesDispatcherProvider.IO()) {
           try {
               val response = repository.getWeatherData(lat = AUSTIN_LAT, long = AUSTIN_LONG)
               var datacounter = -1
               _uiState.value = WeatherUiState.Loaded(
                   WeatherUiModel(
                       city = city,
                       temprature = "${response.current.temp.roundToInt()} F",
                       forecastForWeek = response.daily.map {
                            datacounter++
                           ForecastPerDay(
                               day = Calendar.getInstance().also {cal-> cal.add(Calendar.DATE,datacounter)}
                                   .getDisplayName(
                                       Calendar.DAY_OF_WEEK,
                                       Calendar.LONG,
                                        Locale.getDefault()
                                   ),
                               dayWeather = "${it.temp.day} F",
                               nightWeather = "${it.temp.night} F")
                               }
                   )

               )
           }catch (ex:Exception){
                if (ex is HttpException ){
                    WeatherUiState.Error(ex.message())
                }
           }
        }
    }
    companion object {
        const val AUSTIN_LONG = "-97.733330"
        const val AUSTIN_LAT = "30.266666"
    }
}