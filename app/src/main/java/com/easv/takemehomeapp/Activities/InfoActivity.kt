package com.easv.takemehomeapp.Activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.easv.takemehomeapp.Model.BEPrivilegedUser
import com.easv.takemehomeapp.R
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {
    private lateinit var currentUser : BEPrivilegedUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        imageButton_sms.setOnClickListener {v -> onClickSms()}
        imageButton_email.setOnClickListener {v -> onClickEmail()}

        var extras: Bundle = intent.extras!! // We get the extras sent from the previous activity
        currentUser = extras.getSerializable("user") as BEPrivilegedUser
        Toast.makeText(this, "User name is: ${currentUser.username}", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "User role is: ${currentUser.role}", Toast.LENGTH_SHORT).show()
    }

    private fun onClickSms() {
        TODO("Not yet implemented")
    }

    private fun onClickEmail() {
        TODO("Not yet implemented")
    }
}