package com.example.app_testmobile_suitmedia.ui.Main

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.app_testmobile_suitmedia.R
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

        setupHintBehavior()
    }

    private fun setupHintBehavior() {
        val editTextName = binding.editTextName
        val textInputLayoutName = binding.edtInputLayoutName

        val editTextPalindrome = binding.editTextPalindrome
        val textInputLayoutPalindrome = binding.edtInputLayoutPalindrome

        // Hide hint for Name EditText
        editTextName.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                if (!editTextName.text.isNullOrEmpty()) {
                    textInputLayoutName.hint = ""
                }
            } else {
                textInputLayoutName.hint = getString(R.string.edt_name)
            }
        }

        editTextName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (editTextName.hasFocus()) {
                    textInputLayoutName.hint = ""
                } else {
                    textInputLayoutName.hint = if (s.isNullOrEmpty()) {
                        getString(R.string.edt_name)
                    } else {
                        ""
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Hide hint for Palindrome EditText
        editTextPalindrome.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                if (!editTextPalindrome.text.isNullOrEmpty()) {
                    textInputLayoutPalindrome.hint = ""
                }
            } else {
                textInputLayoutPalindrome.hint = getString(R.string.edt_palindrome)
            }
        }

        editTextPalindrome.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (editTextPalindrome.hasFocus()) {
                    textInputLayoutPalindrome.hint = ""
                } else {
                    textInputLayoutPalindrome.hint = if (s.isNullOrEmpty()) {
                        getString(R.string.edt_palindrome)
                    } else {
                        ""
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
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
