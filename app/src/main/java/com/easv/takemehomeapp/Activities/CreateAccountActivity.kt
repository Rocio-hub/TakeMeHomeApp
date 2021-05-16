package com.easv.takemehomeapp.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.easv.takemehomeapp.Model.BEPrivilegedUser
import com.easv.takemehomeapp.Model.PrivilegedUsers
import com.easv.takemehomeapp.R
import kotlinx.android.synthetic.main.activity_create_account.*

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var newUser: BEPrivilegedUser
    private var privilegedUsersDB: PrivilegedUsers = PrivilegedUsers()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        button_submit.setOnClickListener { v -> onClickSubmit() }

        textView_error2.setVisibility(View.INVISIBLE)
    }

    private fun onClickSubmit() {
        val intent = Intent(this, CodeScannerActivity::class.java)
        if (validateInput()) {
            newUser = BEPrivilegedUser(0, "", "", "")
                newUser.username = editText_username.text.toString()
                newUser.password = editText_password.text.toString()
                newUser.role = "normal"
               // privilegedUsersDB.addNewUser(newUser)
            intent.putExtra("loggedUser", newUser)
            startActivity(intent)
        }
        /*
         * TODO Check if user logged exists on database
         */
    }

    private fun validateInput(): Boolean { //Method that will verify that all information is correct before allowing the user to Create/Update friends
        if (!editText_username.text.isNullOrBlank() && !editText_password.text.isNullOrBlank() && !editText_repPassword.text.isNullOrBlank()) {
            if (editText_password.text.toString() != editText_repPassword.text.toString()) {
                textView_error2.setVisibility(View.VISIBLE)
                textView_error2.postDelayed({
                    textView_error2.setVisibility(View.INVISIBLE)
                }, 4000)
                return false
            }
            return true
        }
        showMissingInfo()
        return false
    }

    private fun showMissingInfo() {
        if (editText_username.text.isNullOrBlank()) {
            editText_username.error = "Please enter a valid Name"
        } else editText_username.error = null
        if (editText_password.text.isNullOrBlank()) {
            editText_password.error = "Please enter a valid password"
        } else editText_username.error = null
        if (editText_repPassword.text.isNullOrBlank()) {
            editText_repPassword.error = "Please enter a valid password"
        } else editText_repPassword.error = null
    }
}
