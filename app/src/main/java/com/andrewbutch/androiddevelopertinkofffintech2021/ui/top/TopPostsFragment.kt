package com.andrewbutch.androiddevelopertinkofffintech2021.ui.top

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.andrewbutch.androiddevelopertinkofffintech2021.ui.BaseFragment

class TopPostsFragment : BaseFragment() {
    private val TAG = TopPostsFragment::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(viewModelStore, viewModelFactory)
                .get(TopPostsViewModel::class.java)
    }
}