package com.example.mobapputb.repositories

import com.example.mobapputb.models.WeatherDataModel
import com.example.mobapputb.services.ApiService

class WeatherRepository (private val apiService: ApiService) {

    suspend fun getWeatherPack (latitude: Float, longitude: Float, temperature: Boolean, precipProb: Boolean, visibility: Boolean) : WeatherDataModel? {

        val hourlyList = mutableListOf<String>()

        if (temperature) {
            hourlyList.add("temperature_2m")
        }
        if (precipProb) {
            hourlyList.add("precipitation_probability")
        }
        if (visibility) {
            hourlyList.add("visibility")
        }

        var hourly = hourlyList.joinToString(separator = ",")
        val response = apiService.getWeatherPack(latitude, longitude, hourly)

        if(response.isSuccessful) {
            return response.body()
        } else {
            return null
        }
    }
}