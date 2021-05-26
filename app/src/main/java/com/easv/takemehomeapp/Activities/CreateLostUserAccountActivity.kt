package com.easv.takemehomeapp.Activities

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.easv.takemehomeapp.Data.IUserDAO
import com.easv.takemehomeapp.Data.UserDAO_Impl
import com.easv.takemehomeapp.Model.BELostUser
import com.easv.takemehomeapp.R
import kotlinx.android.synthetic.main.activity_create_lostuser_account.*
import java.io.File


class CreateLostUserAccountActivity : AppCompatActivity() {

    private var newUser = BELostUser(0, "", "", "", "", "", 0, "", "", "", 0)
    private lateinit var lostUserDB: IUserDAO
    private val REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_lostuser_account)

        lostUserDB = UserDAO_Impl(this)
        newUser.picture = 0

        ib_profilePicture.setImageResource(R.drawable.addcameraicon)

        ib_profilePicture.setOnClickListener { v -> onClickPicture() }
        button_submit.setOnClickListener { v -> onClickSubmit() }

    }

    private fun onClickSubmit() {
          if (validateInput()) {
              var phoneList = editText_phoneList.text.toString()
              var medicationList = editText_medicationList.text.toString()
              var allergiesList = editText_allergyList.text.toString()
              var diseasesList = editText_diseaseList.text.toString()

              newUser.firstName  = editText_fullName.text.toString().split(" ")[0]
              newUser.lastName  = editText_fullName.text.toString().split(" ")[1]
              newUser.phoneList = phoneList
              newUser.email = editText_email.text.toString()
              newUser.address = editText_address.text.toString()
              newUser.CPR = editText_cpr.text.toString().toLong()
              newUser.medicationList = medicationList
              newUser.allergiesList = allergiesList
              newUser.diseasesList = diseasesList
          }

          lostUserDB.createLostUser(newUser)
          var list : BELostUser = lostUserDB.getLostUserById(4)
          Toast.makeText(this, list.firstName, Toast.LENGTH_SHORT).show()
          Toast.makeText(this, "New account created successfully", Toast.LENGTH_SHORT).show()
          finish()

          /*
           * TODO Check if user created exists on database already
           */
    }

    private fun onClickPicture() {
        val intent = Intent(this, CameraActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun validateInput(): Boolean { //Method that will verify that all information is correct before allowing the user to Create/Update friends
        showMissingInfo()
        return true
    }

    private fun showMissingInfo() {
        if (editText_fullName.text.isNullOrBlank()) {
            editText_fullName.error = "Please enter a name"
        } else editText_fullName.error = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) { //Method that will check that the CameraActivity will return a picture and assign it to the lost user as well as display it on the activity
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            var newPicture = data?.extras?.getSerializable("newPicture") as File
            if(newPicture != null) {
                ib_profilePicture.setImageDrawable(Drawable.createFromPath(newPicture.toString()))
                newUser.picture = newPicture.toString().toInt()
            }
        }
    }
}
