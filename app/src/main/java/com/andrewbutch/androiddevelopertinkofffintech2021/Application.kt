package com.andrewbutch.androiddevelopertinkofffintech2021

import android.app.Application
import com.andrewbutch.androiddevelopertinkofffintech2021.api.ApiBuilder
import com.andrewbutch.androiddevelopertinkofffintech2021.repository.PostsDatabase
import com.andrewbutch.androiddevelopertinkofffintech2021.repository.PostsRepository
import com.andrewbutch.androiddevelopertinkofffintech2021.ui.ViewModelFactory

class BaseApplication : Application() {

    private fun getPostsDatabase() = PostsDatabase.getDatabase(applicationContext)

    private fun getApiService() = ApiBuilder.getService()

    private fun  getPostsRepository(): PostsRepository =
        PostsRepository.getInstance(getApiService(), getPostsDatabase())

    fun getViewModelFactory(): ViewModelFactory {
        return ViewModelFactory(getPostsRepository())
    }

}