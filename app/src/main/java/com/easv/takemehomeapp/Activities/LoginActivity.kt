package com.easv.takemehomeapp.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.easv.takemehomeapp.Data.IUserDAO
import com.easv.takemehomeapp.Data.UserDAO_Impl
import com.easv.takemehomeapp.Model.BEPrivilegedUser
import com.easv.takemehomeapp.R
import kotlinx.android.synthetic.main.activity_login.*

//private var privUsersDB: PrivilegedUsers = PrivilegedUsers()

private lateinit var usersDB: IUserDAO
private lateinit var privUserDB: IUserDAO
private var loggedUser: BEPrivilegedUser? = null

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usersDB = UserDAO_Impl(this)
        privUserDB = UserDAO_Impl(this)

        textView_error.setVisibility(View.INVISIBLE)

        insertTestData()

        button_submit.setOnClickListener { v -> onClickSubmit() }
        textView_signUp.setOnClickListener { v -> onClickSignUp() }
    }

    private fun insertTestData() {
        val listOfPhones = "50204598 20159748 31265487 40516581 42965214 49346582"
        val listOfMedication = "metformin enalapril"
        val listOfAllergies = "peanut peony"
        val listOfDiseases = "diabetes hypertension"
        /*
         usersDB.emptyDb()
         usersDB.insertLostUser(BELostUser(1, "Veronica Tapp", listOfPhones, "verotapp@easv", "Copenhague, Denmark", 1309501234, listOfMedication, listOfAllergies, listOfDiseases, File("")))
         usersDB.insertLostUser(BELostUser(2, "Ava Brown", listOfPhones, "avabrow@easv", "Aarhus, Denmark", 2101509546, listOfMedication, listOfAllergies, listOfDiseases, File("")))
         usersDB.insertLostUser(BELostUser(3, "Bob Smith", listOfPhones, "bobsmit@easv", "Esbjerg, Denmark", 1610493216, listOfMedication, listOfAllergies, listOfDiseases, File("")))
         usersDB.insertLostUser(BELostUser(4, "John Davis", listOfPhones, "johndavi@easv", "Aalborg, Denmark", 228565498, listOfMedication, listOfAllergies, listOfDiseases, File("")))
         usersDB.insertLostUser(BELostUser(5, "Silvana Verner", listOfPhones, "silvvern@easv", "Vejle, Denmark", 2508453216, listOfMedication, listOfAllergies, listOfDiseases, File("")))
         usersDB.insertLostUser(BELostUser(6, "Thomas Jones", listOfPhones, "thomjone@easv", "Kolding, Denmark", 1306403165, listOfMedication, listOfAllergies, listOfDiseases, File("")))
         usersDB.insertLostUser(BELostUser(7, "Michael Garcia", listOfPhones, "michgarc@easv", "Randers, Denmark", 2903476589, listOfMedication, listOfAllergies, listOfDiseases, File("")))
         usersDB.insertPrivilegedUser(BEPrivilegedUser(1, "kate1982", "abcd", "Kate", "Underwood", 1205823568, "normal", "", null))
         usersDB.insertPrivilegedUser(BEPrivilegedUser(2, "justin1973", "abcd", "Justin", "Dawson", 1709732955, "normal", "", null))
         usersDB.insertPrivilegedUser(BEPrivilegedUser(3, "elizabeth1981", "abcd", "Elizabeth", "Elliott", 25031981, "doctor", "", null))
         usersDB.insertPrivilegedUser(BEPrivilegedUser(4, "dylan1986", "abcd", "Dylan", "Wells", 11119186, "doctor", "", null))
         usersDB.insertPrivilegedUser(BEPrivilegedUser(5, "AB1234", "abcd", "Scarlett", "Kent", 1408663654, "police", "Kolding station", null))
         usersDB.insertPrivilegedUser(BEPrivilegedUser(6, "CD9876", "abcd", "Luca", "Taylor", 28121965, "police", "Esbjerg station",null))
 */
    }

    private fun onClickSignUp() {
        val intent = Intent(this, SignUpNormalUserActivity::class.java)
        startActivity(intent)
    }

    private fun onClickSubmit() {
        if (validateInput()) {
            val username = editText_user.text.toString()
            val password = editText_password.text.toString()
            loggedUser = privUserDB.login(username, password)
            if (loggedUser != null) {
                val intent = Intent(this, CodeScannerActivity::class.java)
                intent.putExtra("loggedUser", loggedUser)
                startActivity(intent)
                editText_user.getText().clear()
                editText_password.getText().clear()
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
            } else {
                textView_error.setVisibility(View.VISIBLE)
                textView_error.postDelayed({
                    textView_error.setVisibility(View.INVISIBLE)
                }, 4000)
            }
        } else {
            textView_error.setVisibility(View.VISIBLE)
            textView_error.postDelayed({
                textView_error.setVisibility(View.INVISIBLE)
            }, 4000)
        }
    }

    // Checking if the input in form is valid
    fun validateInput(): Boolean {
        if (editText_user.text.toString() == "") {
            editText_user.error = "Please, enter a username"
            return false
        }
        if (editText_password.text.toString() == "") {
            editText_password.error = "Please, enter a password"
            return false
        }
        return true
    }

    override fun onBackPressed() {

    }
}