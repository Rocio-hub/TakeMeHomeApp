package com.easv.takemehomeapp.Activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.util.Linkify
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.easv.takemehomeapp.Model.BEPrivilegedUser
import com.easv.takemehomeapp.Model.BEUser
import com.easv.takemehomeapp.Model.Users
import com.easv.takemehomeapp.R
import kotlinx.android.synthetic.main.activity_info.*


class InfoActivity : AppCompatActivity() {
    private var lostUsersDB: Users = Users()
    private lateinit var loggedUser: BEPrivilegedUser
    private lateinit var lostUser: BEUser
    private var lostUserId: Int = 0
    private var currentLocationLat: Double = 0.0
    private var currentLocationLon: Double = 0.0
    private var myLocationListener: LocationListener? = null //Initialize the Location Listener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        requestPermissions()
        startListening()

        textView_phone.setOnClickListener { v -> onClickPhone() }
        imageButton_sms.setOnClickListener { v -> onClickSms() }
        imageButton_email.setOnClickListener { v -> onClickEmail() }
        imageButton_map.setOnClickListener { v -> onClickMap() }
        button_additInfo.setOnClickListener { v -> onClickAdditInfo() }

        var extras: Bundle = intent.extras!! // We get the extras sent from the previous activity
        lostUserId = extras.getInt("lostUserId")
        lostUser = lostUsersDB.getLostUser(lostUserId)
        loggedUser = extras.getSerializable("user") as BEPrivilegedUser
        Toast.makeText(this, "User name is: ${loggedUser.username}", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "User role is: ${loggedUser.role}", Toast.LENGTH_SHORT).show()

        if (loggedUser.role != "police" && loggedUser.role != "medicalStaff") button_additInfo.setVisibility(
            View.GONE
        )

        getUserInfo()
    }

    private val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private fun requestPermissions() {
        if (!isPermissionGiven()) {
            Toast.makeText(this, "Permission to use GPS denied", Toast.LENGTH_SHORT).show()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) requestPermissions(permissions, 1)
        } else Toast.makeText(this, "Permission to use GPS granted", Toast.LENGTH_SHORT).show()
    }

    private fun isPermissionGiven(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return permissions.all { p -> checkSelfPermission(p) == PackageManager.PERMISSION_GRANTED }
        return true
    }

    private fun getUserInfo() {
        if (lostUser != null) {
            textView_name.text = "${lostUser.firstName} ${lostUser.lastName}"
            textView_phone.text = "${lostUser.phone}"
            Linkify.addLinks(textView_phone, Linkify.ALL);
        } else {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onClickPhone() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel: ${lostUser.phone}")
        startActivity(intent)
    }

    private fun onClickSms() {
        var mapsLink: String =
            "https://www.google.com/maps/search/?api=1&query=$currentLocationLat,$currentLocationLon"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("sms: ${lostUser.phone}")
        intent.putExtra(
            "sms_body",
            "Hello, this is my location via sms: $mapsLink"
        )
        startActivity(intent)
    }

    private fun onClickEmail() {
        var mapsLink =
            "https://www.google.com/maps/search/?api=1&query=$currentLocationLat,$currentLocationLon"
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "plain/text"
        val receivers = arrayOf(lostUser.email)
        intent.putExtra(Intent.EXTRA_EMAIL, receivers)
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "Hello, this is my location via email: $mapsLink"
        )
        startActivity(intent)
    }

    private fun onClickMap() {
        val intent = Intent(this, MapsActivity::class.java)
        intent.putExtra("currentLocationLat", currentLocationLat)
        intent.putExtra("currentLocationLon", currentLocationLon)
        startActivity(intent)
    }

    private fun onClickAdditInfo() {
        TODO("Not yet implemented")
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        /*     if (!isPermissionGiven())
                 Toast.makeText(this, "You dont have permission for using GPS", Toast.LENGTH_SHORT)
                     .show()

             val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
             val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

             //handle cases: location can be null
             if (location != null) {
                 currentLocationLat = location.latitude
                 currentLocationLon = location.longitude
             }*/
    }

    @SuppressLint("MissingPermission")
    //Method that will listen and set the location
    private fun startListening() {
        if (!isPermissionGiven()) return

        if (myLocationListener == null)
            myLocationListener = object : LocationListener {

                override fun onLocationChanged(location: Location) {
                    currentLocationLat = location.latitude
                    currentLocationLon = location.longitude
                }

                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                }
            }

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            0,
            0.0F,
            myLocationListener!!
        )
    }

    override fun onBackPressed() {
        //app wont go back to login
    }

}