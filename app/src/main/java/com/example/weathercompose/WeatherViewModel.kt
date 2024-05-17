package com.example.weathercompose

import android.util.Log
import androidx.lifecycle.ViewModel

class WeatherViewModel : ViewModel() {

    fun getData(city: String){
        Log.i("City name: " , city)
    }


}