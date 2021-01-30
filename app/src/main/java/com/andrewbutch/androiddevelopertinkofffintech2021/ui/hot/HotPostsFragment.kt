package com.andrewbutch.androiddevelopertinkofffintech2021.ui.hot

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.andrewbutch.androiddevelopertinkofffintech2021.ui.BaseFragment
import com.bumptech.glide.RequestManager

class HotPostsFragment(requestManager: RequestManager) : BaseFragment(requestManager) {

    private lateinit var viewModel: HotPostsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(viewModelStore, ViewModelProvider.NewInstanceFactory())
                .get(HotPostsViewModel::class.java)
    }

    override fun subscribeObservers() {
        // TODO
    }
}