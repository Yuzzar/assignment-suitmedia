package com.example.app_testmobile_suitmedia.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app_testmobile_suitmedia.data.api.ApiConfig
import com.example.app_testmobile_suitmedia.data.response.UserResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {

    private val _users = MutableLiveData<UserResponse>()
    val users: LiveData<UserResponse> get() = _users

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchUsers(page: Int, perPage: Int) {
        _loading.value = true
        ApiConfig().getApiService().getUsers(page, perPage).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _loading.value = false
                if (response.isSuccessful) {
                    _users.value = response.body()
                } else {
                    _error.value = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _loading.value = false
                _error.value = t.message ?: "Unknown error"
            }
        })
    }
}
