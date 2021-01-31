package com.andrewbutch.androiddevelopertinkofffintech2021.api.response

import com.google.gson.annotations.Expose

data class ApiResult(
    @Expose val result: List<NetworkPost>
) {
}