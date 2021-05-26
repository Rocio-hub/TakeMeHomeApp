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
        textView_username.setText(loggedUser.firstName)
        textView_role.setText("${loggedUser.role}")
        textView_identityNo.setText("${loggedUser.username}")
        textView_fullName.setText("${loggedUser.firstName} ${loggedUser.lastName}")
        textView_cpr.setText(Integer.toString(loggedUser.CPR))
        textView_station.setText(loggedUser.station)
        imageButton_profilePicture.setImageDrawable(getResources().getDrawable(loggedUser.picture))
    }
}