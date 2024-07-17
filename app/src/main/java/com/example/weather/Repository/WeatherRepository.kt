package com.example.weather.Repository

import com.example.weather.Server.ApiServices

class WeatherRepository(val api: ApiServices) {

    fun getCurrentWeather(lat: Double, lng: Double, unit: String) =
        api.getCurrentWeather(lat, lng, unit, "26299f1ef6b8dc5670ba662a7f6c9144")

    fun getForecastWeather(lat: Double, lng: Double, unit: String) =
        api.getForcastWeather(lat, lng, unit, "26299f1ef6b8dc5670ba662a7f6c9144")
}