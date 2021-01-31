package com.andrewbutch.androiddevelopertinkofffintech2021.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.andrewbutch.androiddevelopertinkofffintech2021.R
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.fragment_main.*

abstract class BaseFragment(val requestManager: RequestManager) : Fragment() {

    protected var cornerRadius: Int = 5
    protected lateinit var viewModel: BaseViewModel


    protected fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    protected fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
    }

    private fun subscribeObservers() {
        subscribePosts()
        subscribeLoading()
        subscribeError()
        subscribePage()
    }

    private fun subscribePosts() {
        viewModel.post.observe(viewLifecycleOwner) { post ->
            description.text = post.description
            requestManager
                .load(post.gifURL)
                .transform(RoundedCorners(cornerRadius))
                .into(postContainer)
        }
    }

    private fun subscribeLoading() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                showProgressBar()
            } else {
                hideProgressBar()
            }
        }
    }

    private fun subscribeError() {
        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun subscribePage() {
        viewModel.page.observe(viewLifecycleOwner) { page ->
            if (page <= 1) {
                disablePreviousButton()
            } else {
                enablePreviousButton()
            }
        }
    }

    private fun disablePreviousButton() {
        btn_prev.apply {
            setOnClickListener(null)
            isClickable = false
            isEnabled = false
        }
    }

    private fun enablePreviousButton() {
        btn_prev.apply {
            setOnClickListener {
                viewModel.getPreviousPost()
            }
            isClickable = true
            isEnabled = true
        }
    }

}