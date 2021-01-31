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
        increasePage()
        val source = postsRepository.getNextPost()
        source.onEach {
            withContext(Dispatchers.Main) {
                handleResult(it)
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
            Resource.Status.LOADING -> setLoading(true)
            Resource.Status.ERROR -> {
                result.message?.let {
                    setError(it)
                }
            }
        }
    }

    override fun getPreviousPost() {
        decreasePage()
        val source = postsRepository.getPreviousPost()
        source.onEach {
            withContext(Dispatchers.Main) {
                handleResult(it)
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))

    }

    override fun retry() {
        val source = postsRepository.getNextPost()
        source.onEach {
            withContext(Dispatchers.Main) {
                handleResult(it)
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }
}
