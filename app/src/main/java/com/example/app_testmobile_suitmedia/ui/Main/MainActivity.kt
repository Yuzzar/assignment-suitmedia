package com.example.app_testmobile_suitmedia.ui.Main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.app_testmobile_suitmedia.databinding.ActivityMainBinding
import com.example.app_testmobile_suitmedia.ui.secondscreen.SecondScreen


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCheck.setOnClickListener {
            checkPalindrome()
        }

        binding.buttonNext.setOnClickListener {
            goToSecondScreen()
        }
    }

    private fun checkPalindrome() {
        val palindromeText = binding.editTextPalindrome.text.toString()
        val isPalindrome = isPalindrome(palindromeText)

        val message = if (isPalindrome) "isPalindrome" else "not palindrome"
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun isPalindrome(text: String): Boolean {
        val cleanText = text.replace("\\s".toRegex(), "").lowercase()
        return cleanText == cleanText.reversed()
    }

    private fun goToSecondScreen() {
        val username = binding.editTextName.text.toString()
        val intent = Intent(this, SecondScreen::class.java).apply {
            putExtra("USER_NAME", username)
        }
        startActivity(intent)
    }
}
