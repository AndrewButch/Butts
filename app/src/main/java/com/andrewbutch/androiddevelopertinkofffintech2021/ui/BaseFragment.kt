package com.andrewbutch.androiddevelopertinkofffintech2021.ui

import android.content.Context
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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_main.*

abstract class BaseFragment() : Fragment() {

    private var cornerRadius: Int = 5
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

    private fun initGlide(): RequestManager {
        val options = RequestOptions()
            .error(R.drawable.ic_outline_compare_arrows_24)
        return Glide.with(requireActivity())
            .setDefaultRequestOptions(options)
    }

}