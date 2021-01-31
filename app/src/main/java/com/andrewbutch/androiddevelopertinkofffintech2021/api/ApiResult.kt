package com.andrewbutch.androiddevelopertinkofffintech2021.api

import com.google.gson.annotations.Expose

data class ApiResult(
    @Expose val result: List<NetworkPost>
) {
}