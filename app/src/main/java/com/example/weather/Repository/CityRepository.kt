package com.example.weather.Repository

import com.example.weather.Server.ApiServices

class CityRepository(val api: ApiServices) {

    fun getCities(q: String, limit: Int) = api.getCitiesList(q, limit, "26299f1ef6b8dc5670ba662a7f6c9144")
}