package com.example.mobapputb.domains

data class WeatherDomain(
    val timestamp: List<String>,
    val temperature: WeatherFloatDomain,
    val precipProb: WeatherIntDomain,
    val visibility: WeatherIntDomain
)

data class WeatherIntDomain(
    val unit: String,
    val value: List<Int>
)

data class WeatherFloatDomain(
    val unit: String,
    val value: List<Float>
)