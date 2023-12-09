package com.example.mobapputb.models

data class WeatherDataModel(
    val latitude: Double,
    val longitude: Double,
    val hourlyUnits: HourlyUnits,
    var hourly: Hourly
)

data class HourlyUnits (
    val temperature: String,
    val precipProb: String,
    val visibility: String
)

data class Hourly (
    val time: List<String>,
    val temperature: List<Double>,
    val precipProb: List<Int>,
    val visibility: List<Int>
)
