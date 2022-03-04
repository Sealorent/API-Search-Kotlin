package com.example.githubapi_search.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapi_search.data.network.ApiService
import com.example.githubapi_search.data.network.Resource
import com.example.githubapi_search.data.network.RetrofitConfig
import com.example.githubapi_search.model.SearchResponse
import com.example.githubapi_search.model.UserGit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel (){

    private val retrofit: ApiService = RetrofitConfig.create()
    private val listUser = MutableLiveData<Resource<List<UserGit>>>()

    fun searchUser(query: String): LiveData<Resource<List<UserGit>>> {
        listUser.postValue(Resource.Loading())
        retrofit.searchUsers(query).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                val list = response.body()?.items
                if (list.isNullOrEmpty())
                    listUser.postValue(Resource.Error(null))
                else
                    listUser.postValue(Resource.Success(list))
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                listUser.postValue(Resource.Error(t.message))
            }

        })
        return listUser
    }
}