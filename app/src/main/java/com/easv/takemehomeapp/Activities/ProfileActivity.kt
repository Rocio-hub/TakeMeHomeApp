package com.easv.takemehomeapp.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.easv.takemehomeapp.Model.BEPrivilegedUser
import com.easv.takemehomeapp.R
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var loggedUser: BEPrivilegedUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        var extras: Bundle = intent.extras!! // We get the extras sent from the previous activity

        loggedUser = extras.getSerializable("loggedUser") as BEPrivilegedUser
        //imageView_profilePicture.setImageDrawable()
        tv_username.text = loggedUser.username
        tv_name.text = "Get name from loggeduser"
        tv_cpr.text = "Get CPR form loggeduser"
    }
}