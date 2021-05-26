package com.easv.takemehomeapp.Activities

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.easv.takemehomeapp.Data.IUserDAO
import com.easv.takemehomeapp.Data.UserDAO_Impl
import com.easv.takemehomeapp.Model.BEPrivilegedUser
import com.easv.takemehomeapp.R
import kotlinx.android.synthetic.main.activity_create_lostuser_account.button_submit
import kotlinx.android.synthetic.main.activity_create_lostuser_account.ib_profilePicture
import kotlinx.android.synthetic.main.activity_sign_up_normal_user.*
import java.io.File

class SignUpNormalUserActivity : AppCompatActivity() {

    private var newUser = BEPrivilegedUser(0, "", "", "", "",  0, "", "", 0)
    private lateinit var normalUserDB: IUserDAO
    private val REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_normal_user)

        normalUserDB = UserDAO_Impl(this)
        newUser.role = "normal"
        newUser.picture = 0

        ib_profilePicture.setImageResource(R.drawable.addcameraicon)

        ib_profilePicture.setOnClickListener { v -> onClickPicture() }
        button_submit.setOnClickListener { v -> onClickSubmit() }
    }

    private fun onClickSubmit() {
        if (validateInput()) {
            newUser.username  = editText_username.text.toString()
            newUser.password  = editText_password.text.toString()
            newUser.firstName  = editText_firstName.text.toString()
            newUser.lastName  = editText_lastName.text.toString()
            newUser.CPR  = editText_cpr.text.toString().toInt()
        }

        normalUserDB.createPrivilegedUser(newUser)

        val intent = Intent(this, CodeScannerActivity::class.java)
        intent.putExtra("loggedUser", newUser)
        startActivity(intent)

        /*
         * TODO Check if user created exists on database already
         */
    }

    private fun validateInput(): Boolean {
        showMissingInfo()
        return true
    }

    private fun showMissingInfo() {
       /* if (editText_fullName.text.isNullOrBlank()) {
            editText_fullName.error = "Please enter a name"
        } else editText_fullName.error = null */
    }

    private fun onClickPicture() {
        val intent = Intent(this, CameraActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) { //Method that will check that the CameraActivity will return a picture and assign it to the lost user as well as display it on the activity
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            var newPicture = data?.extras?.getSerializable("newPicture") as File
            if(newPicture != null) {
                ib_profilePicture.setImageDrawable(Drawable.createFromPath(newPicture.toString()))
                newUser.picture = newPicture as Int
            }
        }
    }
}