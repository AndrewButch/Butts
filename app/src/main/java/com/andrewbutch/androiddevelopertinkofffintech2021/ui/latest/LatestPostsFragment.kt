package com.andrewbutch.androiddevelopertinkofffintech2021.ui.latest

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.andrewbutch.androiddevelopertinkofffintech2021.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_main.*

class LatestPostsFragment : BaseFragment() {
    private val TAG = LatestPostsFragment::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(viewModelStore, viewModelFactory)
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