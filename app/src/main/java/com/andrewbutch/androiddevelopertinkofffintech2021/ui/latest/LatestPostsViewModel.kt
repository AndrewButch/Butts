package com.andrewbutch.androiddevelopertinkofffintech2021.ui.latest

import com.andrewbutch.androiddevelopertinkofffintech2021.api.*
import com.andrewbutch.androiddevelopertinkofffintech2021.ui.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class LatestPostsViewModel : BaseViewModel() {

    override fun getNextPost() {
        CoroutineScope(Dispatchers.IO).launch {
            setLoading(true)
            val rawResponse = ApiBuilder.getService().randomPost().awaitResponse()
            val apiResponse = ApiResponse.create(rawResponse)
            when (apiResponse) {
                is ApiSuccessResponse -> {
                    // get data from ApiResponse
                    val networkPost = apiResponse.body
                    // save data into local database

                    // get updated data from database

                    // return success
                    setPost(networkPost)
                }
                is ApiEmptyResponse -> {
                }
                is ApiErrorResponse -> {
                    setError(apiResponse.errorMessage)
                }
            }
            setLoading(false)
            increasePage()
        }

    }

    override fun getPreviousPost() {
        CoroutineScope(Dispatchers.IO).launch {
            setLoading(true)
            val rawResponse = ApiBuilder.getService().randomPost().awaitResponse()
            val apiResponse = ApiResponse.create(rawResponse)
            when (apiResponse) {
                is ApiSuccessResponse -> {
                    // get data from ApiResponse
                    val networkPost = apiResponse.body
                    // save data into local database

                    // get updated data from database

                    // return success
                    setPost(networkPost)
                }
                is ApiEmptyResponse -> {
                }
                is ApiErrorResponse -> {
                    setError(apiResponse.errorMessage)
                }
            }
            setLoading(false)
            decreasePage()
        }
    }
}