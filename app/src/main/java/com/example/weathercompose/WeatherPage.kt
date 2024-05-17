package com.example.weathercompose

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun WeatherPage( weatherViewModel: WeatherViewModel) {
    var city by rememberSaveable {
        mutableStateOf("")
    }


    Row(
       modifier = Modifier.fillMaxSize().padding(20.dp),
       horizontalArrangement = Arrangement.Center
   ) {
       OutlinedTextField(
           value = city,
           onValueChange = {
               city = it
           },
           label = {
               Text(text = "Search Location ")
           },
           singleLine = true,      // giriş bilgilerinin tek bir satırda kalmasını
           maxLines = 1,          //burada da yine aynı şekilde kaç satır izin veriyorsak ölçebiliyoruz

           leadingIcon = {
               IconButton(onClick = {

               }) {
                   Icon(imageVector = Icons.Filled.LocationOn,
                       contentDescription = "Location Icon")
               }
           },

           trailingIcon = {
               IconButton(onClick = {
                   weatherViewModel.getData(city)
               }) {
                   Icon(imageVector = Icons.Default.Search,
                       contentDescription = "Searched" )
               }
           },

           keyboardOptions = KeyboardOptions(
               keyboardType = KeyboardType.Text,
               imeAction = ImeAction.Search
           ),

           keyboardActions = KeyboardActions(
               onSearch = {
                   Log.i("ImeAction", "clicked")
               }
           )

       )
   }

}

@Composable
@Preview
fun showWeatherPage() {
    WeatherPage(weatherViewModel = WeatherViewModel())
}