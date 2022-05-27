package com.example.weatherappcompose.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherappcompose.R
import com.example.weatherappcompose.datasource.WeatherUiState
import com.example.weatherappcompose.ui.theme.WeatherAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CurrentWeatherHeader()
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun CurrentWeatherHeader(mainViewModel: MainViewModel = viewModel()) {
    val heighInPx = with(LocalDensity.current){
                LocalConfiguration.current.screenHeightDp.dp.toPx()
    }
    Column (
         Modifier.background(
            Brush.verticalGradient(
            listOf(Color.Transparent,Color.Gray,Color.Black),
                0f,
                heighInPx*1.1f
            ))
            ){
        when(val state = mainViewModel.uiState.collectAsState().value){
            is WeatherUiState.Empty -> Text(
                text = "There No Data",
                modifier = Modifier.padding(16.dp))

            is WeatherUiState.Loading -> Column (
                   Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                    ){
                    CircularProgressIndicator()
            }
            is WeatherUiState.Error -> Text(
                text = state.message,
                modifier = Modifier.padding(16.dp)
                )
            is WeatherUiState.Loaded -> showData(state.data)


        }

    }


}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun showData(data:WeatherUiModel){
    Text(text = data.city,
        Modifier.padding(24.dp),
        style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold))

    Text(data.temprature,
        modifier = Modifier.padding(start = 24.dp, bottom = 100.dp),
        style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.secondary)
    )

    LazyVerticalGrid(cells = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 12.dp)
    ){
        items(items = data.forecastForWeek, itemContent = {card->
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = card.day)
                
                    Row() {
                        Icon(painter = painterResource(id = R.drawable.ic_day) , contentDescription = "day",
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Text(text = card.dayWeather)
                    }
                    Row() {
                       Icon(painter = painterResource(id = R.drawable.ic_night), contentDescription ="night" ,
                       modifier = Modifier.padding(top = 4.dp)
                           )
                        Text(text = card.nightWeather)
                    }
                }
        })
    }
}