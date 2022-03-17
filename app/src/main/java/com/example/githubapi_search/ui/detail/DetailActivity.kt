package com.example.githubapi_search.ui.detail


import com.example.githubapi_search.databinding.ActivityDetailBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubapi_search.ui.adapter.FollowPagerAdapter
import com.example.githubapi_search.data.network.Resource
import com.example.githubapi_search.model.UserGit
import com.example.githubapi_search.util.Const.EXTRA_USER
import com.example.githubapi_search.util.Const.TAB_TITLES
import com.example.githubapi_search.util.ViewStateCallback
import com.google.android.material.tabs.TabLayoutMediator


class DetailActivity : AppCompatActivity() , ViewStateCallback<UserGit?> {


    private lateinit var detailBinding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            elevation = 0f
        }

        val username = intent.getStringExtra(EXTRA_USER)

        viewModel.getDetailUser(username).observe(this, {
            when (it) {
                is Resource.Error -> onFailed(it.message)
                is Resource.Loading -> onLoading()
                is Resource.Success -> onSuccess(it.data)
            }
        })

        val pageAdapter = FollowPagerAdapter(this, username.toString())

        detailBinding.apply {
            viewPager.adapter = pageAdapter
            TabLayoutMediator(tabs, viewPager) { tabs, position ->
                tabs.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onSuccess(data: UserGit?) {
        detailBinding.apply {
            detailProgressBar.visibility = invisible
            userTotalRepo.text = data?.repository.toString()
            userTotalFollowers.text = data?.follower.toString()
            userTotalFollowing.text = data?.following.toString()
            userName.text = data?.name
            userCompany.text = data?.company
            userLocation.text = data?.location

            Glide.with(this@DetailActivity)
                .load(data?.avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(userImage)

            supportActionBar?.title = data?.username
        }
    }

    override fun onLoading() {
        detailBinding.apply {
            detailProgressBar.visibility = visible
            userName.visibility = invisible
            userCompany.visibility = invisible
            userLocation.visibility = invisible
            userFollowing.visibility = invisible
            userTotalFollowing.visibility = invisible
            userRepo.visibility = invisible
            userTotalRepo.visibility = invisible
            userFollowers.visibility = invisible
            userTotalFollowers.visibility = invisible
            userImage.visibility =invisible
        }
    }

    override fun onFailed(message: String?) {
    }
}


