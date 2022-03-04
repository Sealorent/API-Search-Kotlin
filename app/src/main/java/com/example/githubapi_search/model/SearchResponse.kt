package com.example.githubapi_search.model

import com.google.gson.annotations.SerializedName

data class SearchResponse (
        @field:SerializedName("items")
        val items: List<UserGit>
)