package com.example.weathercompose

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch

@Composable
fun WeatherPage() {
    var city by rememberSaveable {
        mutableStateOf("")
    }


    val focusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()


    Row(
       modifier = Modifier.fillMaxSize(),
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
                   coroutineScope.launch {
                       focusRequester.requestFocus()
                   }
               }) {
                   Icon(imageVector = Icons.Filled.LocationOn,
                       contentDescription = "Location Icon")
               }
           },

           trailingIcon = {
               IconButton(onClick = { /*TODO*/ }) {
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
           ),
           modifier = Modifier.focusRequester(focusRequester)

       )
   }

}

@Composable
@Preview
fun showWeatherPage() {
    WeatherPage()
}