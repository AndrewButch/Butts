package com.andrewbutch.androiddevelopertinkofffintech2021.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andrewbutch.androiddevelopertinkofffintech2021.repository.PostsRepository
import com.andrewbutch.androiddevelopertinkofffintech2021.ui.latest.LatestPostsViewModel
import com.andrewbutch.androiddevelopertinkofffintech2021.ui.top.TopPostsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val postsRepository: PostsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            LatestPostsViewModel::class.java -> LatestPostsViewModel(postsRepository) as T
            TopPostsViewModel::class.java -> TopPostsViewModel(postsRepository) as T
            else -> throw IllegalArgumentException("Unknown view model class $modelClass")
        }
    }
}