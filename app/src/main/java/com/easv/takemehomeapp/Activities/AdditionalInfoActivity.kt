package com.easv.takemehomeapp.Activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.easv.takemehomeapp.Model.BELostUser
import com.easv.takemehomeapp.Model.BEPrivilegedUser
import com.easv.takemehomeapp.R
import kotlinx.android.synthetic.main.activity_additional_info.*

class AdditionalInfoActivity : AppCompatActivity() {

    private lateinit var loggedUser: BEPrivilegedUser
    private lateinit var lostUser: BELostUser
    private var phoneList = listOf<String>()
    private var allergieList = listOf<String>()
    private var medicationList = listOf<String>()
    private var diseaseList = listOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_additional_info)

        button_allergies.setOnClickListener { v -> onClickAllergies() }
        button_diseases.setOnClickListener { v -> onClickDiseases() }
        button_medication.setOnClickListener { v -> onClickMedication() }

        var extras: Bundle = intent.extras!! // We get the extras sent from the previous activity
        loggedUser = extras.getSerializable("loggedUser") as BEPrivilegedUser
        lostUser = extras.getSerializable("lostUser") as BELostUser

        textView_username.setText("${lostUser.firstName} ${lostUser.lastName}")

        if (loggedUser.role.equals("police")) {
            textView_cpr.setText((lostUser.CPR).toString())
            textView_address.setText(lostUser.address)
            textView_email.setText(lostUser.email)
            button_allergies.isVisible = false
            button_diseases.isVisible = false
            button_medication.isVisible = false
        }

        if(loggedUser.role.equals("doctor")) {
            label.setText("MEDICAL INFORMATION")
            line1.isVisible = false
            line2.isVisible = false
            line3.isVisible = false
            line4.isVisible = false
            label_cpr.isVisible = false
            label_address.isVisible = false
            label_phoneList.isVisible = false
            label_email.isVisible = false
            textView_cpr.isVisible = false
            textView_address.isVisible = false
            spinner_phoneList.isVisible = false
            textView_email.isVisible = false
        }

        phoneList = lostUser.phoneList.split(" ")
        allergieList = lostUser.allergiesList.split(" ")
        medicationList = lostUser.medicationList.split(" ")
        diseaseList = lostUser.diseasesList.split(" ")
        setPhoneSpinner()
    }

    private fun onClickAllergies() {
        val intent = Intent(this, ListsActivity::class.java)
        intent.putExtra("lostUser", lostUser)
        intent.putExtra("listName", "allergies")
        startActivity(intent)
    }

    private fun onClickDiseases() {
        val intent = Intent(this, ListsActivity::class.java)
        intent.putExtra("lostUser", lostUser)
        intent.putExtra("listName", "diseases")
        startActivity(intent)
    }

    private fun onClickMedication() {
        val intent = Intent(this, ListsActivity::class.java)
        intent.putExtra("lostUser", lostUser)
        intent.putExtra("listName", "medication")
        startActivity(intent)
    }

    private fun setPhoneSpinner() {
        if (spinner_phoneList != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, phoneList)
            spinner_phoneList.adapter = adapter
            spinner_phoneList.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position != 0) {
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data =
                            Uri.parse("tel: ${spinner_phoneList.getItemAtPosition(position)}")
                        startActivity(intent)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }
    }
}