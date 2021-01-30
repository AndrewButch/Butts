package com.andrewbutch.androiddevelopertinkofffintech2021.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.andrewbutch.androiddevelopertinkofffintech2021.R
import com.andrewbutch.androiddevelopertinkofffintech2021.ui.hot.HotPostsFragment
import com.andrewbutch.androiddevelopertinkofffintech2021.ui.latest.LatestPostsFragment
import com.andrewbutch.androiddevelopertinkofffintech2021.ui.top.TopPostsFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions

private val TAB_TITLES = arrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2,
        R.string.tab_text_3,
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager)
    : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var requestManager: RequestManager

    init {
        requestManager = initGlide()
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> LatestPostsFragment(requestManager)
            1 -> HotPostsFragment(requestManager)
            2 -> TopPostsFragment(requestManager)
            else -> throw IndexOutOfBoundsException("Wrong page position")
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }

    private fun initGlide(): RequestManager {
        val options = RequestOptions()
            .error(R.drawable.ic_outline_compare_arrows_24)
        return Glide.with(context)
            .setDefaultRequestOptions(options)
    }
}