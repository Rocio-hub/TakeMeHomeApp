package com.easv.takemehomeapp.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.easv.takemehomeapp.Data.IUserDAO
import com.easv.takemehomeapp.Data.UserDAO_Impl
import com.easv.takemehomeapp.Model.BELostUser
import com.easv.takemehomeapp.Model.BEPrivilegedUser
import com.easv.takemehomeapp.R
import kotlinx.android.synthetic.main.activity_login.*

//private var privUsersDB: PrivilegedUsers = PrivilegedUsers()

private lateinit var usersDB: IUserDAO
private lateinit var prvilegedUser : BEPrivilegedUser
private lateinit var privUserDB: IUserDAO
private var loggedUser: BEPrivilegedUser? = null
//private var lostUserId: Int = 0

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usersDB = UserDAO_Impl(this)
        privUserDB = UserDAO_Impl(this)
        insertTestData()

        textView_error.setVisibility(View.INVISIBLE)

        button_submit.setOnClickListener { v -> onClickSubmit() }
        textView_createAccount.setOnClickListener { v -> onClickCreateAccount() }

        //var extras: Bundle = intent.extras!!
        //lostUserId = extras.getInt("lostUserId")
    }

    private fun insertTestData() {
        usersDB.emptyDb()
        usersDB.insertLostUser(BELostUser(1, "Peter", "Parker", 111, "p@p"))
        usersDB.insertLostUser(BELostUser(2, "Scarlet", "Witch", 222, "s@w"))
        usersDB.insertLostUser(BELostUser(3, "Captain", "America", 333, "c@a"))
        usersDB.insertPrivilegedUser(BEPrivilegedUser(1, "pol1", "p", "police"))
        usersDB.insertPrivilegedUser(BEPrivilegedUser(2, "doc1", "p", "doctor"))
        usersDB.insertPrivilegedUser(BEPrivilegedUser(3, "nor1", "p", "normal"))
    }

    private fun onClickCreateAccount() {
        val intent = Intent(this, CreateAccountActivity::class.java)
        startActivity(intent)
    }

    private fun onClickSubmit() {
        if (validateInput()) {
            val username = editText_user.text.toString()
            val password = editText_password.text.toString()
            loggedUser = privUserDB.login(username, password)
            if (loggedUser != null) {
                val intent = Intent(this, CodeScannerActivity::class.java)
                intent.putExtra("loggedUser", loggedUser)
                startActivity(intent)
                editText_user.getText().clear()
                editText_password.getText().clear()
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
        if (editText_user.text.toString() == "") {
            editText_user.error = "Please, enter a username"
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