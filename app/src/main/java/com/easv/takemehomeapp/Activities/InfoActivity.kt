package com.easv.takemehomeapp.Activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.easv.takemehomeapp.Model.BEPrivilegedUser
import com.easv.takemehomeapp.Model.BEUser
import com.easv.takemehomeapp.Model.Users
import com.easv.takemehomeapp.R
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {
    private var lostUsersDB : Users = Users()
    private lateinit var loggedUser : BEPrivilegedUser
    private lateinit var lostUser: BEUser
    private var lostUserId: Int =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        imageButton_sms.setOnClickListener {v -> onClickSms()}
        imageButton_email.setOnClickListener {v -> onClickEmail()}

        var extras: Bundle = intent.extras!! // We get the extras sent from the previous activity
        lostUserId = extras.getInt("lostUserId")
        lostUser = lostUsersDB.getLostUser(lostUserId)
        loggedUser = extras.getSerializable("user") as BEPrivilegedUser
        Toast.makeText(this, "User name is: ${loggedUser.username}", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "User role is: ${loggedUser.role}", Toast.LENGTH_SHORT).show()
        getUserInfo()
    }

    private fun getUserInfo() {
        if(lostUser != null) {
            textView_name.text = "${lostUser.firstName} ${lostUser.lastName}"
            textView_phone.text = "${lostUser.phone}"
        }else{
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
        }
    }


    private fun onClickSms() {
        TODO("Not yet implemented")
    }

    private fun onClickEmail() {
        TODO("Not yet implemented")
    }

    override fun onBackPressed() {
        //app wont go back to login
    }
}