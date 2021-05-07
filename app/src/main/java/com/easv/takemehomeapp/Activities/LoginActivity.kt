package com.easv.takemehomeapp.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.easv.takemehomeapp.Model.PrivilegedUsers
import com.easv.takemehomeapp.R
import kotlinx.android.synthetic.main.activity_login.*

private var privUsersDB: PrivilegedUsers = PrivilegedUsers()
//private var lostUserId: Int = 0

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        textView_error.setVisibility(View.INVISIBLE)

        button_submit.setOnClickListener { v -> onClickSubmit() }
        textView_createAccount.setOnClickListener { v -> onClickCreateAccount() }

        //var extras: Bundle = intent.extras!!
        //lostUserId = extras.getInt("lostUserId")
    }

    private fun onClickCreateAccount() {
        val intent = Intent(this, CreateAccountActivity::class.java)
        startActivity(intent)
    }

    private fun onClickSubmit() {
        if (validateInput()) {
            val username = editText_name.text.toString()
            val password = editText_password.text.toString()
            if (privUsersDB.checkUserExists(username, password) != null) {
                val intent = Intent(this, CodeScannerActivity::class.java)
                intent.putExtra("loggedUser", privUsersDB.checkUserExists(username, password))
                startActivity(intent)
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
            } else {
                textView_error.setVisibility(View.VISIBLE)
                textView_error.postDelayed({
                    textView_error.setVisibility(View.INVISIBLE)
                }, 4000)
            }
        } else {
            textView_error.setVisibility(View.VISIBLE)
            textView_error.postDelayed({
                textView_error.setVisibility(View.INVISIBLE)
            }, 4000)
        }
    }

    // Checking if the input in form is valid
    fun validateInput(): Boolean {
        if (editText_name.text.toString() == "") {
            editText_name.error = "Please, enter a username"
            return false
        }
        if (editText_password.text.toString() == "") {
            editText_password.error = "Please, enter a password"
            return false
        }
        return true
    }

    /*override fun onBackPressed() {
        ProcessPhoenix.triggerRebirth(getApplicationContext());
    }*/
}