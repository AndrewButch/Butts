package com.andrewbutch.androiddevelopertinkofffintech2021.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.andrewbutch.androiddevelopertinkofffintech2021.BaseApplication
import com.andrewbutch.androiddevelopertinkofffintech2021.R
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.layout_connection_error.*

abstract class BaseFragment() : Fragment() {

    protected lateinit var viewModel: BaseViewModel
    private lateinit var requestManager: RequestManager
    protected lateinit var viewModelFactory: ViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestManager = initGlide()
    }

    protected fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    protected fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModelFactory = (requireActivity().application as BaseApplication).getViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.getNextPost()
        }
        btnNext.setOnClickListener {
            viewModel.getNextPost()
        }
        btnPrev.setOnClickListener {
            viewModel.getPreviousPost()
        }

        retryBtn.setOnClickListener {
            viewModel.retry()
        }
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
                .load(post.gifUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        hideProgressBar()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        hideProgressBar()
                        return false
                    }

                })
                .into(postContainer)
            hideErrorConnection()
        }
    }

    private fun subscribeLoading() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                showProgressBar()
            }
        }
    }

    private fun subscribeError() {
        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error.contains("Unable to resolve")) {
                showErrorConnection()
            } else {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun subscribePage() {
        viewModel.page.observe(viewLifecycleOwner) { page ->
            if (page > 0) {
                enablePreviousButton()
            } else {
                disablePreviousButton()
            }
        }
    }

    private fun disablePreviousButton() {
        btnPrev.apply {
            setOnClickListener(null)
            isClickable = false
            isEnabled = false
        }
    }

    private fun enablePreviousButton() {
        btnPrev.apply {
            setOnClickListener {
                viewModel.getPreviousPost()
            }
            isClickable = true
            isEnabled = true
        }
    }

    private fun initGlide(): RequestManager {
        val options = RequestOptions()
            .error(R.drawable.ic_outline_compare_arrows_24)
        return Glide.with(requireActivity())
            .setDefaultRequestOptions(options)
    }

    private fun showErrorConnection() {
        connectionError.visibility = View.VISIBLE
    }

    private fun hideErrorConnection() {
        connectionError.visibility = View.GONE
    }

}