package com.easv.takemehomeapp.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.easv.takemehomeapp.R
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        imageButton_sms.setOnClickListener {v -> onClickSms()}
        imageButton_email.setOnClickListener {v -> onClickEmail()}
    }

    private fun onClickSms() {
        TODO("Not yet implemented")
    }

    private fun onClickEmail() {
        TODO("Not yet implemented")
    }
}