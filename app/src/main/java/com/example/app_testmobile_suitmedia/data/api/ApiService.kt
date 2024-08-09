package com.example.app_testmobile_suitmedia.data.api

import com.example.app_testmobile_suitmedia.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<UserResponse>
}
