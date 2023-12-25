package com.example.mobapputb.networks

import com.google.gson.annotations.SerializedName

data class WeatherDataModel(
    val latitude: Float,
    val longitude: Float,
    @SerializedName("hourly_units")
    val hourlyUnits: HourlyUnits,
    var hourly: Hourly
)

data class HourlyUnits (
    val time: String,
    @SerializedName("temperature_2m")
    val temperature: String,
    @SerializedName("precipitation_probability")
    val precipProb: String,
    val visibility: String
)

data class Hourly (
    val time: List<String>,
    @SerializedName("temperature_2m")
    val temperature: List<Float>,
    @SerializedName("precipitation_probability")
    val precipProb: List<Int>,
    val visibility: List<Int>
)
