package com.andrewbutch.androiddevelopertinkofffintech2021.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.andrewbutch.androiddevelopertinkofffintech2021.R
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.fragment_main.*

abstract class BaseFragment(val requestManager: RequestManager) : Fragment() {

    protected var cornerRadius: Int = 5

    abstract fun subscribeObservers()

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
}