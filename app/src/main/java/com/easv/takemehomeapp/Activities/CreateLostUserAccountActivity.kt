package com.easv.takemehomeapp.Activities

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.easv.takemehomeapp.Data.IUserDAO
import com.easv.takemehomeapp.Data.UserDAO_Impl
import com.easv.takemehomeapp.Model.BELostUser
import com.easv.takemehomeapp.Model.BEPrivilegedUser
import com.easv.takemehomeapp.R
import kotlinx.android.synthetic.main.activity_create_lostuser_account.*
import java.io.File
import java.lang.Double


class CreateLostUserAccountActivity : AppCompatActivity() {

    private var newUser = BELostUser(0, "", "", "", "", 0, "", "", "", File(""))
    private lateinit var lostUserDB: IUserDAO
    private lateinit var loggedUser: BEPrivilegedUser
    private val REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_lostuser_account)

        var extras: Bundle = intent.extras!!
        loggedUser = extras.getSerializable("loggedUser") as BEPrivilegedUser

        lostUserDB = UserDAO_Impl(this)

        imageButton_profilePicture.setImageResource(R.drawable.addcameraicon)
        imageButton_profilePicture.setOnClickListener { v -> onClickPicture() }
        button_submit.setOnClickListener { v -> onClickSubmit() }
    }

    private fun onClickSubmit() {
        if (validateInput()) {
            var phoneList = editText_phoneList.text.toString()
            var medicationList = editText_medicationList.text.toString()
            var allergiesList = editText_allergyList.text.toString()
            var diseasesList = editText_diseaseList.text.toString()

            newUser.fullName = editText_fullName.text.toString().split(" ")[0]
            newUser.phoneList = phoneList
            newUser.email = editText_email.text.toString()
            newUser.address = editText_address.text.toString()
            newUser.CPR = editText_cpr.text.toString().toLong()
            newUser.medicationList = medicationList
            newUser.allergiesList = allergiesList
            newUser.diseasesList = diseasesList
            lostUserDB.createLostUser(newUser)
            Toast.makeText(this, "New account created successfully", Toast.LENGTH_SHORT).show()

            var intent = Intent(this, CodeScannerActivity::class.java)
            intent.putExtra("loggedUser", loggedUser)
            startActivity(intent)
        }
    }

    private fun onClickPicture() {
        val intent = Intent(this, CameraActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun validateInput(): Boolean { //Method that will verify that all information is correct before allowing the user to submit
        if (editText_fullName.text.toString() == "" || checkContainNumber(editText_fullName.text.toString())) {
            editText_fullName.error =
                "Please, enter a value for first name and last name."
            return false
        }
        try {
            Double.parseDouble(editText_cpr.text.toString())
        } catch (e: NumberFormatException) {
            editText_cpr.error =
                "Please, enter enter a value for the CPR. It must be a number and not contain spaces."
        }
        if (editText_cpr.text.toString() == "" || editText_cpr.text.toString().contains(" ")) {
            editText_cpr.error =
                "Please, enter enter a value for the CPR. It must be a number and not contain spaces."
            return false
        }
        if (editText_address.text.toString() == "") {
            editText_address.error = "Please, enter enter a value for the address"
            return false
        }
        if (editText_email.text.toString() == "" || !editText_email.text.toString()
                .contains("@") || !editText_email.text.toString().contains(".")
        ) {
            editText_email.error = "Please, enter enter a value for the email."
            return false
        }
        try {
            Double.parseDouble(editText_phoneList.text.toString())
        } catch (e: NumberFormatException) {
            editText_phoneList.error =
                "Please, enter enter a value for the phone numbers."
        }
        if (editText_phoneList.text.toString() == "") {
            editText_phoneList.error =
                "Please, enter enter a value for the list of phones. Remember to separate them using a space."
            return false
        }
        if (editText_medicationList.text.toString() == "") {
            editText_medicationList.error =
                "Please, enter enter a value for the list of medicines. Remember to separate them using a space."
            return false
        }
        if (editText_allergyList.text.toString() == "") {
            editText_allergyList.error =
                "Please, enter enter a value for the list of ellergies. Remember to separate them using a space."
            return false
        }
        if (editText_diseaseList.text.toString() == "") {
            editText_diseaseList.error =
                "Please, enter enter a value for the list of diseases. Remember to separate them using a space."
            return false
        }
        if (newUser.picture.length()<1){
            Toast.makeText(this, "Please, provide a picture", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
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
                imageButton_profilePicture.setImageDrawable(Drawable.createFromPath(newPicture.toString()))
                newUser.picture = newPicture
                lostUserDB.updateLostUser(newUser)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("fullName", editText_fullName.text.toString())
        outState.putString("cpr", editText_cpr.text.toString())
        outState.putString("address", editText_address.text.toString())
        outState.putString("email", editText_email.text.toString())
        outState.putString("phoneList", editText_phoneList.text.toString())
        outState.putString("medicationList", editText_medicationList.text.toString())
        outState.putString("allergiesList", editText_allergyList.text.toString())
        outState.putString("diseasesList", editText_diseaseList.text.toString())
        outState.putString("picture", newUser.picture.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        editText_fullName.setText(savedInstanceState.getString("fullName"))
        editText_cpr.setText(savedInstanceState.getString("cpr"))
        editText_address.setText(savedInstanceState.getString("address"))
        editText_email.setText(savedInstanceState.getString("email"))
        editText_phoneList.setText(savedInstanceState.getString("phoneList"))
        editText_medicationList.setText(savedInstanceState.getString("medicationList"))
        editText_allergyList.setText(savedInstanceState.getString("allergiesList"))
        editText_diseaseList.setText(savedInstanceState.getString("diseasesList"))
        imageButton_profilePicture.setImageDrawable(Drawable.createFromPath("picture"))
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
