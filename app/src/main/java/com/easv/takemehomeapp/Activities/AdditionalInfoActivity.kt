package com.easv.takemehomeapp.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.easv.takemehomeapp.Model.BELostUser
import com.easv.takemehomeapp.Model.BEPrivilegedUser
import com.easv.takemehomeapp.R

class AdditionalInfoActivity : AppCompatActivity() {

    private lateinit var loggedUser: BEPrivilegedUser
    private lateinit var lostUser: BELostUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_additional_info)

        var extras: Bundle = intent.extras!! // We get the extras sent from the previous activity
        loggedUser = extras.getSerializable("loggedUser") as BEPrivilegedUser
        lostUser = extras.getSerializable("lostUser") as BELostUser

        //textView_firstName.text = "First Name: ${lostUser.firstName}"
        //textView_lastName.text = "Last Name: ${lostUser.lastName}"
        //textView_address.text = "Address: ${lostUser.phone}"

        if(loggedUser.role.equals("police")){
            //TODO info only available for police
        }
        if(loggedUser.role.equals("doctor")){
            //TODO info only available for doctor
        }
    }
}