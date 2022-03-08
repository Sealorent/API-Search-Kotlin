package com.example.githubapi_search.ui.followers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapi_search.data.network.Resource
import com.example.githubapi_search.data.network.RetrofitConfig
import com.example.githubapi_search.model.UserGit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class followersViewModel: ViewModel(){

    private val retrofit = RetrofitConfig.create()
    private val listUser = MutableLiveData<Resource<List<UserGit>>>()

    fun getUserFollowers(username: String): LiveData<Resource<List<UserGit>>> {
        listUser.postValue(Resource.Loading())
        retrofit.getUserFollowers(username).enqueue(object : Callback<List<UserGit>> {
            override fun onResponse(call: Call<List<UserGit>>, response: Response<List<UserGit>>) {
                val list = response.body()
                if (list.isNullOrEmpty())
                    listUser.postValue(Resource.Error(null))
                else
                    listUser.postValue(Resource.Success(list))
            }
            override fun onFailure(call: Call<List<UserGit>>, t: Throwable) {
                listUser.postValue(Resource.Error(t.message))
            }
        })

        return listUser
    }
}
