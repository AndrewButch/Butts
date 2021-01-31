package com.andrewbutch.androiddevelopertinkofffintech2021.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("random?json=true")
    fun randomPost(): Call<NetworkPost>

    @GET("latest/{page}?json=true")
    fun getLatestPosts(@Path("page") page: Int): Call<ApiResult>

    @GET("hot/{page}?json=true")
    fun getHotPosts(@Path("page") page: Int): Call<ApiResult>

    @GET("top/{page}?json=true")
    fun getTopPosts(@Path("page") page: Int): Call<ApiResult>
}