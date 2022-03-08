package com.example.githubapi_search.util

import androidx.annotation.StringRes
import com.example.githubapi_search.R

object Const {
    const val EXTRA_USER = "EXTRA_USER"
    const val BASE_URL = "https://api.github.com"
    const val TIME_SPLASH : Long = 3000

    @StringRes
    val TAB_TITLES = intArrayOf(
        R.string.followers,
        R.string.following
    )
    const val GITHUB_TOKEN = "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"



}