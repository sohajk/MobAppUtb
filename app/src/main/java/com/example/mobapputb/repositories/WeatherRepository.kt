package com.example.mobapputb.repositories

import com.example.mobapputb.domains.WeatherDomain
import com.example.mobapputb.domains.WeatherFloatDomain
import com.example.mobapputb.domains.WeatherIntDomain
import com.example.mobapputb.services.ApiService

class WeatherRepository (private val apiService: ApiService) {

    suspend fun getWeatherPack (latitude: Float, longitude: Float, temperature: Boolean, precipProb: Boolean, visibility: Boolean) : WeatherDomain? {

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
            val dataAll = response.body()
            val dataTemperature = WeatherFloatDomain(dataAll!!.hourlyUnits.temperature, dataAll!!.hourly.temperature)
            val dataPrecipProb = WeatherIntDomain(dataAll!!.hourlyUnits.precipProb, dataAll!!.hourly.precipProb)
            val dataVisibility = WeatherIntDomain(dataAll!!.hourlyUnits.visibility, dataAll!!.hourly.visibility)

            return  WeatherDomain(dataAll.hourly.time, dataTemperature, dataPrecipProb, dataVisibility)
        } else {
            return null
        }
    }
}