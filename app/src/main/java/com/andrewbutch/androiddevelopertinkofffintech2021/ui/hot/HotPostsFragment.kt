package com.andrewbutch.androiddevelopertinkofffintech2021.ui.hot

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.andrewbutch.androiddevelopertinkofffintech2021.ui.BaseFragment

class HotPostsFragment : BaseFragment() {
    private val TAG = HotPostsFragment::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(viewModelStore, viewModelFactory)
                .get(HotPostsViewModel::class.java)
    }
}