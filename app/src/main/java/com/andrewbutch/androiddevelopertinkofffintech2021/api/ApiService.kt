package com.andrewbutch.androiddevelopertinkofffintech2021.api

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("random?json=true")
    fun randomPost(): Call<NetworkPost>
}