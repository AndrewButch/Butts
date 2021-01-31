package com.andrewbutch.androiddevelopertinkofffintech2021.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andrewbutch.androiddevelopertinkofffintech2021.api.NetworkPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _post = MutableLiveData<NetworkPost>()
    val post: LiveData<NetworkPost> = _post


    protected suspend fun setLoading(isLoading: Boolean) {
        withContext(Dispatchers.Main) {
            _isLoading.value = isLoading
        }
    }

    protected suspend fun setPost(post: NetworkPost) {
        withContext(Dispatchers.Main) {
            _post.value = post
        }
    }

    protected suspend fun setError(msg: String) {
        withContext(Dispatchers.Main) {
            _error.value = msg
        }
    }

    abstract fun getNextPost()

    abstract fun getPreviousPost()
}