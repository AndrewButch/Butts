package com.andrewbutch.androiddevelopertinkofffintech2021.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.andrewbutch.androiddevelopertinkofffintech2021.R
import com.andrewbutch.androiddevelopertinkofffintech2021.ui.latest.LatestPostsFragment
import com.andrewbutch.androiddevelopertinkofffintech2021.ui.top.TopPostsFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,

)

@ExperimentalCoroutinesApi
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> LatestPostsFragment()
            1 -> TopPostsFragment()
            else -> throw IndexOutOfBoundsException("Wrong index: $position in array with size ${TAB_TITLES.size}")
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }


}