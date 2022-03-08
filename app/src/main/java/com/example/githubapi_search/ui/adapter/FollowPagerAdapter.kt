package com.example.githubapi_search.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubapi_search.ui.followers.followers
import com.example.githubapi_search.ui.following.following
import com.example.githubapi_search.util.Const.TAB_TITLES

class FollowPagerAdapter(activity: AppCompatActivity, private val username: String): FragmentStateAdapter(activity){

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = followers.getInstance(username)
            1 -> fragment = following.getInstance(username)
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return TAB_TITLES.size

    }
}