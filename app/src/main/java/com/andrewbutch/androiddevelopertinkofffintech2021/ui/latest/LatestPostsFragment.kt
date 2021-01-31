package com.andrewbutch.androiddevelopertinkofffintech2021.ui.latest

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.andrewbutch.androiddevelopertinkofffintech2021.ViewModelFactory
import com.andrewbutch.androiddevelopertinkofffintech2021.ui.BaseFragment
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
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

    override fun subscribeObservers() {
        viewModel.post.observe(viewLifecycleOwner) { post ->
            Log.d(TAG, "LatestPost: id = ${post.id} url = ${post.gifURL}")
            description.text = post.description
            requestManager
                .load(post.gifURL)
                .transform(RoundedCorners(cornerRadius))
                .into(postContainer)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                showProgressBar()
            } else {
                hideProgressBar()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }


}