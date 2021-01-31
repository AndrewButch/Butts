package com.andrewbutch.androiddevelopertinkofffintech2021.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andrewbutch.androiddevelopertinkofffintech2021.model.Post
import com.andrewbutch.androiddevelopertinkofffintech2021.utils.Resource
import kotlin.math.max

abstract class BaseViewModel : ViewModel() {
    private val TAG = "BaseViewModel"

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post> = _post

    private val _page = MutableLiveData<Int>(-1)
    val page: LiveData<Int> = _page


    protected fun setLoading(isLoading: Boolean) {
        Log.d(TAG, "setLoading: $isLoading")
        _isLoading.value = isLoading
    }

    protected fun setPost(post: Post) {
        _post.value = post
    }

    protected fun setError(msg: String) {
        _error.value = msg
    }

    protected fun increasePage() {
        val currentPage = _page.value
        currentPage?.let {
            _page.value = it + 1
        }
    }

    protected fun decreasePage() {
        val currentPage = _page.value
        currentPage?.let {
            _page.value = max(0, it - 1)
        }
    }

    fun handleResult(result: Resource<Post>) {
        when (result.status) {
            Resource.Status.SUCCESS -> {
                result.data?.let {
                    setPost(it)
                    setLoading(false)
                }
            }
            Resource.Status.LOADING -> setLoading(true)
            Resource.Status.ERROR -> {
                result.message?.let {
                    setError(it)
                }
            }
        }
    }

    abstract fun getNextPost()

    abstract fun getPreviousPost()

    abstract fun retry()

}