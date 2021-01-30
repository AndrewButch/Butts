package com.andrewbutch.androiddevelopertinkofffintech2021.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andrewbutch.androiddevelopertinkofffintech2021.api.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class PageViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _post = MutableLiveData<NetworkPost>()
    val post: LiveData<NetworkPost> = _post

    fun setIsLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun getRandomPost() {
        setIsLoading(true)
        CoroutineScope(IO).launch {
            val rawResponse = ApiBuilder.getService().randomPost().awaitResponse()
            val apiResponse = ApiResponse.create(rawResponse)
            when (apiResponse) {
                is ApiSuccessResponse -> {
                    // get data from ApiResponse
                    val networkPost = apiResponse.body
                    // save data into local database

                    // get updated data from database

                    // return success
                    withContext(Main) {
                        _post.value = networkPost
                    }

                }
                is ApiEmptyResponse -> {
                }
                is ApiErrorResponse -> {
                    withContext(Main) {
                        _error.value = apiResponse.errorMessage
                    }

                }
            }
            withContext(Main) {
                setIsLoading(false)
            }
        }

    }
}