package com.andrewbutch.androiddevelopertinkofffintech2021.ui.top

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.andrewbutch.androiddevelopertinkofffintech2021.ui.BaseFragment
import com.bumptech.glide.RequestManager

class TopPostsFragment(requestManager: RequestManager) : BaseFragment(requestManager) {

    private lateinit var viewModel: TopPostsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(viewModelStore, ViewModelProvider.NewInstanceFactory())
                .get(TopPostsViewModel::class.java)
    }

    override fun subscribeObservers() {
        // TODO
    }
}