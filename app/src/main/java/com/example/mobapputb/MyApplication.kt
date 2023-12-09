package com.example.mobapputb

import android.app.Application
import com.example.mobapputb.repositories.WeatherRepository
import com.example.mobapputb.services.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication : Application() {
    val apiService: ApiService by lazy {
        val refrofit = Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        refrofit.create(ApiService::class.java)
    }

    val weatherRepository: WeatherRepository by lazy {
        WeatherRepository(apiService)
    }

    override fun onCreate() {
        super.onCreate()
    }
}