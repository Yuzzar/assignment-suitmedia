package com.example.app_testmobile_suitmedia.ui.secondscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.app_testmobile_suitmedia.databinding.ActivitySecondScreenBinding
import com.example.app_testmobile_suitmedia.ui.thirdscreen.ThirdScreen

class SecondScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySecondScreenBinding

    companion object {
        private const val REQUEST_CODE_USER_SELECTION = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val username = intent.getStringExtra("USER_NAME") ?: "Default User"
        binding.usernameText.text = username

        binding.chooseUserButton.setOnClickListener {
            val intent = Intent(this, ThirdScreen::class.java)
            startActivityForResult(intent, REQUEST_CODE_USER_SELECTION)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_USER_SELECTION && resultCode == RESULT_OK) {
            val selectedUserName = data?.getStringExtra("SELECTED_USER_NAME")
            selectedUserName?.let {
                updateSelectedUser(it)
            }
        }
    }

    private fun updateSelectedUser(userName: String) {
        binding.selectedUserLabel.text = userName
    }
}
