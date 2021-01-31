package com.andrewbutch.androiddevelopertinkofffintech2021

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andrewbutch.androiddevelopertinkofffintech2021.ui.hot.HotPostsViewModel
import com.andrewbutch.androiddevelopertinkofffintech2021.ui.latest.LatestPostsViewModel
import com.andrewbutch.androiddevelopertinkofffintech2021.ui.top.TopPostsViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            LatestPostsViewModel::class.java -> LatestPostsViewModel() as T
            HotPostsViewModel::class.java -> HotPostsViewModel() as T
            TopPostsViewModel::class.java -> TopPostsViewModel() as T
            else -> throw IllegalArgumentException("Unknown view model class $modelClass")
        }
    }
}