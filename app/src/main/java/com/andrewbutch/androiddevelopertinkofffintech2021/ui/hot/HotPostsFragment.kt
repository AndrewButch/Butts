package com.andrewbutch.androiddevelopertinkofffintech2021.ui.hot

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.andrewbutch.androiddevelopertinkofffintech2021.ViewModelFactory
import com.andrewbutch.androiddevelopertinkofffintech2021.ui.BaseFragment
import com.bumptech.glide.RequestManager

class HotPostsFragment(
    private val viewModelProvider: ViewModelFactory,
    requestManager: RequestManager
) : BaseFragment(requestManager) {
    private val TAG = HotPostsFragment::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(viewModelStore, viewModelProvider)
                .get(HotPostsViewModel::class.java)
    }
}