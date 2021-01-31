package com.andrewbutch.androiddevelopertinkofffintech2021.ui.latest

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.andrewbutch.androiddevelopertinkofffintech2021.ViewModelFactory
import com.andrewbutch.androiddevelopertinkofffintech2021.ui.BaseFragment
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.fragment_main.*

class LatestPostsFragment(
    private val viewModelProvider: ViewModelFactory,
    requestManager: RequestManager
) : BaseFragment(requestManager) {
    private val TAG = LatestPostsFragment::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(viewModelStore, viewModelProvider)
                .get(LatestPostsViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getNextPost()

        btn_next.setOnClickListener {
            viewModel.getNextPost()
        }
    }

}