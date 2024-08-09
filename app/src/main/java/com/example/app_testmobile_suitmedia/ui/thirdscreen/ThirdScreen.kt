package com.example.app_testmobile_suitmedia.ui.thirdscreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_testmobile_suitmedia.data.api.ApiConfig
import com.example.app_testmobile_suitmedia.data.response.DataItem
import com.example.app_testmobile_suitmedia.data.response.UserResponse
import com.example.app_testmobile_suitmedia.databinding.ActivityThirdScreenBinding
import com.example.app_testmobile_suitmedia.ui.adapter.UserAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdScreen : AppCompatActivity() {

    private lateinit var binding: ActivityThirdScreenBinding
    private lateinit var userAdapter: UserAdapter
    private var users = mutableListOf<DataItem>()
    private var currentPage = 1
    private var totalPages = 1
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter(users) { user ->
            // Handle user click
            val intent = Intent().apply {
                putExtra("SELECTED_USER_NAME", "${user.firstName} ${user.lastName}")
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.recyclerViewUsers.apply {
            layoutManager = LinearLayoutManager(this@ThirdScreen)
            adapter = userAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!isLoading && !recyclerView.canScrollVertically(1)) {
                        loadMoreUsers()
                    }
                }
            })
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            refreshUsers()
        }

        loadUsers()
    }

    private fun loadUsers() {
        isLoading = true
        ApiConfig().getApiService().getUsers(currentPage, 10).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { userResponse ->
                        totalPages = userResponse.totalPages ?: 1
                        currentPage = userResponse.page ?: 1
                        userResponse.data?.let {
                            users.addAll(it)
                            userAdapter.notifyDataSetChanged()
                        }
                    }
                }
                isLoading = false
                binding.swipeRefreshLayout.isRefreshing = false
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                isLoading = false
                binding.swipeRefreshLayout.isRefreshing = false

            }
        })
    }

    private fun loadMoreUsers() {
        if (currentPage < totalPages) {
            currentPage++
            loadUsers()
        }
    }

    private fun refreshUsers() {
        currentPage = 1
        users.clear()
        userAdapter.notifyDataSetChanged()
        loadUsers()
    }
}
