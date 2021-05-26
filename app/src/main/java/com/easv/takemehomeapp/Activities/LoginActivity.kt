package com.easv.takemehomeapp.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.easv.takemehomeapp.Data.IUserDAO
import com.easv.takemehomeapp.Data.UserDAO_Impl
import com.easv.takemehomeapp.Model.BELostUser
import com.easv.takemehomeapp.Model.BEPrivilegedUser
import com.easv.takemehomeapp.R
import kotlinx.android.synthetic.main.activity_login.*

//private var privUsersDB: PrivilegedUsers = PrivilegedUsers()

private lateinit var usersDB: IUserDAO
private lateinit var prvilegedUser : BEPrivilegedUser
private lateinit var privUserDB: IUserDAO
private var loggedUser: BEPrivilegedUser? = null
//private var lostUserId: Int = 0

class LoginActivity : AppCompatActivity() {

    private val lostUserPicId = intArrayOf(0, R.drawable.lost1, R.drawable.lost2, R.drawable.lost3, R.drawable.lost4, R.drawable.lost5, R.drawable.lost6, R.drawable.lost7)
    private val PrivUserPicId = intArrayOf(0, R.drawable.normal1, R.drawable.normal2, R.drawable.police1, R.drawable.police2, R.drawable.doctor1, R.drawable.doctor2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usersDB = UserDAO_Impl(this)
        privUserDB = UserDAO_Impl(this)
        insertTestData()

        textView_error.setVisibility(View.INVISIBLE)

        button_submit.setOnClickListener { v -> onClickSubmit() }
        textView_signUp.setOnClickListener { v -> onClickSignUp() }
    }

    private fun insertTestData() {
        val listOfPhones = listOf("50204598 20159748 31265487 40516581 42965214 49346582")
        val listOfMedication = listOf("m e d i c a t i o n")
        val listOfAllergies = listOf("a l l e r g i e s")
        val listOfDiseases = listOf("d i s e a s e s")

        usersDB.emptyDb()
        usersDB.insertLostUser(BELostUser(1, "Veronica", "Tapp", listOfPhones, "verotapp@easv", "Copenhague, Denmark", 1309501234, listOfMedication, listOfAllergies, listOfDiseases, R.drawable.lost1))
        usersDB.insertLostUser(BELostUser(2, "Ava", "Brown", listOfPhones, "avabrow@easv", "Aarhus, Denmark", 2101509546, listOfMedication, listOfAllergies, listOfDiseases, R.drawable.lost2))
        usersDB.insertLostUser(BELostUser(3, "Bob", "Smith", listOfPhones, "bobsmit@easv", "Esbjerg, Denmark", 1610493216, listOfMedication, listOfAllergies, listOfDiseases, R.drawable.lost3))
        usersDB.insertLostUser(BELostUser(4, "John", "Davis", listOfPhones, "johndavi@easv", "Aalborg, Denmark", 228565498, listOfMedication, listOfAllergies, listOfDiseases, R.drawable.lost4))
        usersDB.insertLostUser(BELostUser(5, "Silvana", "Verner", listOfPhones, "silvvern@easv", "Vejle, Denmark", 2508453216, listOfMedication, listOfAllergies, listOfDiseases, R.drawable.lost5))
        usersDB.insertLostUser(BELostUser(6, "Thomas", "Jones", listOfPhones, "thomjone@easv", "Kolding, Denmark", 1306403165, listOfMedication, listOfAllergies, listOfDiseases, R.drawable.lost6))
        usersDB.insertLostUser(BELostUser(7, "Michael", "Garcia", listOfPhones, "michgarc@easv", "Randers, Denmark", 2903476589, listOfMedication, listOfAllergies, listOfDiseases, R.drawable.lost7))
        usersDB.insertPrivilegedUser(BEPrivilegedUser(1, "pol1", "p", "FIRSTNAME1", "LASTNAME1", 123, "police", "station", "PICTURE"))
        usersDB.insertPrivilegedUser(BEPrivilegedUser(2, "doc1", "p", "FIRSTNAME2", "LASTNAME2", 456, "doctor", "station", "PICTURE"))
        usersDB.insertPrivilegedUser(BEPrivilegedUser(3, "nor1", "p", "FIRSTNAME3", "LASTNAME3", 789, "normal", "station", "PICTURE"))
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

    /*override fun onBackPressed() {
        ProcessPhoenix.triggerRebirth(getApplicationContext());
    }*/
}