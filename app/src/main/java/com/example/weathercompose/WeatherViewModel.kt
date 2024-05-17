package com.example.weathercompose

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathercompose.api.NetworkResponse
import com.example.weathercompose.api.RetrofitInstance
import com.example.weathercompose.api.WeatherModel
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val weatherApi = RetrofitInstance.weatherApi
    val apikey = "58960d8ffa1049b8836172025241201"


    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult : LiveData<NetworkResponse<WeatherModel>> = _weatherResult

    fun getData(city: String){
        Log.i("City name: " , city)

        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            val response = weatherApi.getWeather(apikey, city)
            try {
                if (response.isSuccessful){
                    response.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it)
                    }
                }else{
                    _weatherResult.value = NetworkResponse.Error("Failed to load data")
                }
            }catch (e: Exception){
                _weatherResult.value = NetworkResponse.Error("Failed to load data")
                Log.i("CATCH ERROR: ", e.message!!.toString())

            }
        }
    }


}