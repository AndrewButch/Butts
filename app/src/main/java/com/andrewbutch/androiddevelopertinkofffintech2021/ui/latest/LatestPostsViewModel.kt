package com.andrewbutch.androiddevelopertinkofffintech2021.ui.latest

import com.andrewbutch.androiddevelopertinkofffintech2021.model.Post
import com.andrewbutch.androiddevelopertinkofffintech2021.repository.PostsRepository
import com.andrewbutch.androiddevelopertinkofffintech2021.ui.BaseViewModel
import com.andrewbutch.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

class LatestPostsViewModel(private val postsRepository: PostsRepository) : BaseViewModel() {
    override fun getNextPost() {
//        CoroutineScope(Dispatchers.IO).launch {
//            postsRepository.getNextPost().collect {
//                handleResult(it)
//            }
//        }
        val source = postsRepository.getNextPost()
        source.onEach {
            withContext(Dispatchers.Main) {
                handleResult(it)
                increasePage()
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }

    private fun handleResult(result: Resource<Post>) {
        when (result.status) {
            Resource.Status.SUCCESS -> {
                result.data?.let {
                    setPost(it)
                    setLoading(false)
                }
            }
            Resource.Status.LOADING -> setLoading(false)
            Resource.Status.ERROR -> {
                result.message?.let {
                    setError(it)
                }
            }
        }
    }

//    override fun getNextPost() {
//        CoroutineScope(Dispatchers.IO).launch {
//            setLoading(true)
//            val rawResponse = ApiBuilder.getService().randomPost().awaitResponse()
//            val apiResponse = ApiResponse.create(rawResponse)
//            when (apiResponse) {
//                is ApiSuccessResponse -> {
//                    // get data from ApiResponse
//                    val networkPost = apiResponse.body
//                    // save data into local database
//
//                    // get updated data from database
//
//                    // return success
//                    setPost(networkPost)
//                }
//                is ApiEmptyResponse -> {
//                }
//                is ApiErrorResponse -> {
//                    setError(apiResponse.errorMessage)
//                }
//            }
//            setLoading(false)
//            increasePage()
//        }
//
//    }

    override fun getPreviousPost() {
        val source = postsRepository.getNextPost()
        source.onEach {
            withContext(Dispatchers.Main) {
                handleResult(it)
                decreasePage()
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))

    }
}
