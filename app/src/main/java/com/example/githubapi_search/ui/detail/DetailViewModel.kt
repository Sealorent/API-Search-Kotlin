package com.example.githubapi_search.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapi_search.data.network.Resource
import com.example.githubapi_search.data.network.RetrofitConfig
import com.example.githubapi_search.model.UserGit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {

    private val retrofit = RetrofitConfig.create()
    private val userGit = MutableLiveData<Resource<UserGit>>()

    fun getDetailUser(username: String?): LiveData<Resource<UserGit>> {
        retrofit.getDetailUser(username!!).enqueue(object : Callback<UserGit> {
            override fun onResponse(call: Call<UserGit>, response: Response<UserGit>) {
                val result = response.body()
                userGit.postValue(Resource.Success(result))
            }
            override fun onFailure(call: Call<UserGit>, t: Throwable) {
            }
        })
        return userGit
    }
}