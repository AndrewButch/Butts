package com.andrewbutch.androiddevelopertinkofffintech2021.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.andrewbutch.androiddevelopertinkofffintech2021.R
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_main.*

class PostFragment : Fragment() {
    private val TAG = "PlaceholderFragment"

    private lateinit var pageViewModel: PageViewModel
    private lateinit var requestManager: RequestManager
    private var cornerRadius: Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel =
            ViewModelProvider(viewModelStore, ViewModelProvider.NewInstanceFactory()).get(
                PageViewModel::class.java
            )
        requestManager = initGlide()
        cornerRadius = requireContext().resources.getDimensionPixelSize(R.dimen.postCornerRadius)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pageViewModel.getRandomPost()

        subscribeObservers()
    }

    private fun subscribeObservers() {
        pageViewModel.post.observe(viewLifecycleOwner) { post ->
            Log.d(TAG, "onViewCreated: id = ${post.id} url = ${post.gifURL}")
            description.text = post.description
            requestManager
                .load(post.gifURL)
                .transform(RoundedCorners(cornerRadius))
                .into(postContainer)
        }

        pageViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                showProgressBar()
            } else {
                hideProgressBar()
            }
        }

        pageViewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {

        private const val ARG_SECTION_TYPE = "section_type"

        @JvmStatic
        fun newInstance(sectionType: Int): PostFragment {
            return PostFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_TYPE, sectionType)
                }
            }
        }
    }

    private fun initGlide(): RequestManager {
        val options = RequestOptions()
            .error(R.drawable.ic_outline_compare_arrows_24)
        return Glide.with(this)
            .setDefaultRequestOptions(options)
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }
}