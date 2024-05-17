package com.example.weathercompose

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weathercompose.api.NetworkResponse
import com.example.weathercompose.api.WeatherModel

@Composable
fun WeatherPage( weatherViewModel: WeatherViewModel) {
    var city by rememberSaveable {
        mutableStateOf("")
    }

    val weatherResult = weatherViewModel.weatherResult.observeAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF191970)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        SearchBar(
            city = city,
            onCityChange = { city = it },
            onSearch = { weatherViewModel.getData(city) }
        )


        when(val result = weatherResult.value){
            is NetworkResponse.Error -> {
                Text(text = result.message )
            }
            NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }
            is NetworkResponse.Success -> {
                WeatherDetails(data = result.data)
            }
            null -> {}
        }
    }

}

@Composable
fun WeatherDetails(data: WeatherModel){

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(imageVector = Icons.Default.LocationOn,
                contentDescription ="Location Icon",
                modifier = Modifier.size(35.dp),
                tint = Color.White
            )
            Spacer(modifier = Modifier.padding(1.dp))
            Text(text = data.location.name + ",", fontSize = 29.sp ,color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(1.dp))
            Text(text = data.location.country, fontSize = 29.sp ,color = Color.White, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Card(
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .background(Color(0xFFB0E2FF)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(text = "${data.current.temp_c} °C",
                    fontSize = 65.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.padding(10.dp))

                AsyncImage(
                    modifier = Modifier.size(160.dp),
                    model = "https:${data.current.condition.icon}"
                        .replace("64x64","128x128"),
                    contentDescription = "Weather Image"
                )
                Spacer(modifier = Modifier.padding(2.dp))
                Text(text = data.current.condition.text,
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.padding(15.dp))


        Card() {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .background(Color(0xFFB0E2FF))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    CardText(key ="Humidity", value =data.current.humidity )
                    CardText(key ="Wind Speed", value =data.current.wind_kph )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    CardText(key ="Feels Like °C", value =data.current.feelslike_c )
                    CardText(key ="Feels Like °F", value =data.current.feelslike_c )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    CardText(key ="Cloud", value =data.current.cloud )
                    CardText(key ="UV", value =data.current.uv )
                }
            }

        }
    }

}

@Composable
fun CardText(key: String, value: String) {
    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = key, fontSize = 26.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
        Text(text = value, fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color.White)
    }
}

@Composable
fun SearchBar(
    city: String,
    onCityChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = city,
            onValueChange = onCityChange,
            label = { Text(text = "Search Location", color = Color.White) },
            singleLine = true,
            maxLines = 1,
            leadingIcon = {
                IconButton(onClick = {
                    // Location icon action
                }) {
                    Icon(imageVector = Icons.Filled.LocationOn, contentDescription = "Location Icon", tint = Color.White)
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    onSearch()  // Invoke the onSearch function
                    keyboardController?.hide()  // Hide the keyboard
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon", tint = Color.White)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    Log.i("ImeAction", "clicked")
                    onSearch()  // Invoke the onSearch function
                    keyboardController?.hide()  // Hide the keyboard
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF63B8FF)),
            textStyle = androidx.compose.ui.text.TextStyle(color = Color.White)
        )
    }
}

@Composable
@Preview
fun showWeatherPage() {
    WeatherPage(weatherViewModel = WeatherViewModel())
}