package com.easv.takemehomeapp.Activities

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.easv.takemehomeapp.Data.IUserDAO
import com.easv.takemehomeapp.Data.UserDAO_Impl
import com.easv.takemehomeapp.Model.BEPrivilegedUser
import com.easv.takemehomeapp.R
import kotlinx.android.synthetic.main.activity_create_lostuser_account.*
import kotlinx.android.synthetic.main.activity_create_lostuser_account.button_submit
import kotlinx.android.synthetic.main.activity_sign_up_normal_user.*
import kotlinx.android.synthetic.main.activity_sign_up_normal_user.editText_cpr
import java.io.File
import java.lang.Double.parseDouble

class SignUpNormalUserActivity : AppCompatActivity() {

    private var newUser = BEPrivilegedUser(0, "", "", "", "", 0, "", "", null)
    private lateinit var normalUserDB: IUserDAO
    private val REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_normal_user)

        normalUserDB = UserDAO_Impl(this)
        newUser.role = "normal"
        newUser.picture = null
        newUser.station = ""

        imageButton_picture.setOnClickListener { v -> onClickPicture() }
        button_submit.setOnClickListener { v -> onClickSubmit() }
    }

    private fun onClickSubmit() {
        if (validateInput()) {
            newUser.username = editText_username.text.toString()
            newUser.password = editText_password.text.toString()
            newUser.firstName = editText_firstName.text.toString()
            newUser.lastName = editText_lastName.text.toString()
            newUser.CPR = editText_cpr.text.toString().toInt()
            normalUserDB.createPrivilegedUser(newUser)
            val intent = Intent(this, CodeScannerActivity::class.java)
            intent.putExtra("loggedUser", newUser)
            startActivity(intent)
        }
    }

    private fun validateInput(): Boolean {
        if (editText_firstName.text.toString() == "" || checkContainNumber(editText_firstName.text.toString()) || editText_firstName.text.toString()
                .contains(" ")
        ) {
            editText_firstName.error =
                "Please, enter a value for the first name. It cannot contain numbers or spaces."
            return false
        }
        if (editText_lastName.text.toString() == "" || checkContainNumber(editText_lastName.text.toString()) || editText_lastName.text.toString()
                .contains(" ")
        ) {
            editText_lastName.error =
                "Please, enter a value for the last name. It cannot contain numbers or spaces."
            return false
        }
        try {
            parseDouble(editText_cpr.text.toString())
        } catch (e: NumberFormatException) {
            editText_cpr.error =
                "Please, enter enter a value for the CPR. It must be a number and not contain spaces."
        }
        if (editText_cpr.text.toString() == "" || editText_cpr.text.toString().contains(" ")) {
            editText_cpr.error =
                "Please, enter enter a value for the CPR. It must be a number and not contain spaces."
            return false
        }
        if (editText_username.text.toString() == "") {
            editText_username.error = "Please, enter enter a value for the username"
            return false
        }
        if (editText_password.text.toString() == "") {
            editText_password.error = "Please, enter enter a value for the password"
            return false
        }
        if (editText_repPassword.text.toString() != editText_password.text.toString()) {
            editText_repPassword.error = "Passwords do not match"
            return false
        }
        return true
    }

    private fun onClickPicture() {
        val intent = Intent(this, CameraActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) { //Method that will check that the CameraActivity will return a picture and assign it to the lost user as well as display it on the activity
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            var newPicture = data?.extras?.getSerializable("newPicture") as File
            if (newPicture != null) {
                imageButton_picture.setImageDrawable(Drawable.createFromPath(newPicture?.absolutePath))
                newUser.picture = newPicture
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("firstName", editText_firstName.text.toString())
        outState.putString("lastName", editText_lastName.text.toString())
        outState.putString("cpr", editText_cpr.text.toString())
        outState.putString("username", editText_username.text.toString())
        outState.putString("password", editText_password.text.toString())
        outState.putString("repPassword", editText_repPassword.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        editText_firstName.setText(savedInstanceState.getString("firstName"))
        editText_lastName.setText(savedInstanceState.getString("lastName"))
        editText_cpr.setText(savedInstanceState.getString("cpr"))
        editText_username.setText(savedInstanceState.getString("username"))
        editText_password.setText(savedInstanceState.getString("password"))
        editText_repPassword.setText(savedInstanceState.getString("repPassword"))
    }

    private fun checkContainNumber(value: String): Boolean {
        var containNumber: Boolean = false
        for (item in value.toCharArray()) {
            if (Character.isDigit(item).also { containNumber = it }) {
                break
            }
        }
        return containNumber
    }
}