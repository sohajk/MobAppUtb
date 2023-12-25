package com.example.mobapputb.services

import com.example.mobapputb.networks.WeatherDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    companion object {
        const val WEATHER_PACK = "v1/forecast"
    }

    @GET(WEATHER_PACK)
    suspend fun getWeatherPack (
        @Query("latitude") latitude: Float,
        @Query("longitude") longitude: Float,
        @Query("hourly") hourly: String
    ) : Response<WeatherDataModel>
}