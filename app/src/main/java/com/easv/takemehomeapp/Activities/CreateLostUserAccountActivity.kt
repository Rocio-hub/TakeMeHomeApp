package com.easv.takemehomeapp.Activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.easv.takemehomeapp.Data.IUserDAO
import com.easv.takemehomeapp.Data.UserDAO_Impl
import com.easv.takemehomeapp.Model.BELostUser
import com.easv.takemehomeapp.R
import kotlinx.android.synthetic.main.activity_create_lostuser_account.*


class CreateLostUserAccountActivity : AppCompatActivity() {

    private var newUser = BELostUser(0, "", "", "", "", "", 0, "", "", "", "")
    private lateinit var lostUserDB: IUserDAO


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_lostuser_account)

        lostUserDB = UserDAO_Impl(this)

        imageView_profilePicture.setImageResource(R.drawable.addcameraicon)

        button_submit.setOnClickListener { v -> onClickSubmit() }
    }

      private fun onClickSubmit() {
          var phonesResult: List<String> = editText_phoneList.text.toString().split(" ").map { it.trim() }
          var medicationResult: List<String> = editText_medicationList.text.toString().split(" ").map { it.trim() }
          var allergiesResult: List<String> = editText_allergyList.text.toString().split(" ").map { it.trim() }
          var diseasesResult: List<String> = editText_diseaseList.text.toString().split(" ").map { it.trim() }

          if (validateInput()) {
              newUser.firstName  = editText_fullName.text.toString().split(" ")[0]
              newUser.lastName  = editText_fullName.text.toString().split(" ")[1]
              newUser.phoneList = editText_phoneList.text.toString()
              newUser.email = editText_email.text.toString()
              newUser.address = editText_address.text.toString()
              newUser.CPR = editText_cpr.text.toString().toInt()
              newUser.medicationList = editText_medicationList.text.toString()
              newUser.allergiesList = editText_allergyList.text.toString()
              newUser.diseasesList = editText_diseaseList.text.toString()
              newUser.picture = "PICTURE STRING"
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

    private fun validateInput(): Boolean { //Method that will verify that all information is correct before allowing the user to Create/Update friends
        showMissingInfo()
        return true
    }

    private fun showMissingInfo() {
        if (editText_fullName.text.isNullOrBlank()) {
            editText_fullName.error = "Please enter a name"
        } else editText_fullName.error = null
    }
}
