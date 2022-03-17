package com.example.githubapi_search.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapi_search.R
import com.example.githubapi_search.data.network.Resource
import com.example.githubapi_search.databinding.ActivityMainBinding
import com.example.githubapi_search.model.UserGit
import com.example.githubapi_search.ui.adapter.UserGitAdapter
import com.example.githubapi_search.util.ViewStateCallback

class MainActivity : AppCompatActivity() ,ViewStateCallback<List<UserGit>> {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var userQuery: String
    private lateinit var viewModel: MainViewModel
    private lateinit var userGitAdapter: UserGitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        userGitAdapter = UserGitAdapter()
        mainBinding.includePencarian.rvUserGit.apply {
            adapter = userGitAdapter
            layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL  ,false)
        }

        mainBinding.searchView.apply {
            queryHint = resources.getString(R.string.search_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    userQuery = query.toString()
                    clearFocus()
                    viewModel.searchUser(userQuery).observe(this@MainActivity,{
                        when (it){
                            is Resource.Error -> onFailed(it.message)
                            is Resource.Loading -> onLoading()
                            is Resource.Success -> it.data?.let{it1 ->onSuccess(it1) }
                        }
                    })
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }





    override fun onSuccess(data: List<UserGit>) {
        userGitAdapter.setAllData(data)
        mainBinding.includePencarian.apply {
            ivSearchIcon.visibility = invisible
            tvMessage.visibility = invisible
            mainProgressBar.visibility = invisible
            rvUserGit.visibility = visible
        }
    }

    override fun onLoading() {
        mainBinding.includePencarian.apply {
            ivSearchIcon.visibility = invisible
            tvMessage.visibility = invisible
            mainProgressBar.visibility = visible
            rvUserGit.visibility = invisible
        }
    }

    override fun onFailed(message: String?) {
        mainBinding.includePencarian.apply {
            //User yang dicari tidak ditemukan
            if (message == null) {
                ivSearchIcon.apply {
                    setImageResource(R.drawable.ic_search_empty)
                    visibility = visible
                }
                tvMessage.apply {
                    text = resources.getString(R.string.user_not_found)
                    visibility = visible
                }
                //Memunculkan error pada exception
            } else {
                ivSearchIcon.apply {
                    setImageResource(R.drawable.ic_search_failed)
                    visibility = visible
                }
                tvMessage.apply {
                    text = message
                    visibility = visible
                }
            }
            mainProgressBar.visibility = invisible
            rvUserGit.visibility = invisible
        }

    }
}