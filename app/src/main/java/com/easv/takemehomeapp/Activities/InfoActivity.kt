package com.easv.takemehomeapp.Activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.util.Linkify
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.easv.takemehomeapp.Model.BELostUser
import com.easv.takemehomeapp.Model.BEPrivilegedUser
import com.easv.takemehomeapp.Model.LostUsers
import com.easv.takemehomeapp.R
import kotlinx.android.synthetic.main.activity_info.*
import java.io.File


class InfoActivity : AppCompatActivity() {

    private val REQUEST_CODE = 101

    private var lostUsersDB: LostUsers = LostUsers()
    private lateinit var loggedUser: BEPrivilegedUser
    private lateinit var lostUser: BELostUser
    private var lostUserId: Int = 0
    private var currentLocationLat: Double = 0.0
    private var currentLocationLon: Double = 0.0
    private var myLocationListener: LocationListener? = null //Initialize the Location Listener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        requestPermissions()
        startListening()
        getLocation()

        imageButton_profilePicture.setOnClickListener { v -> onClickProfilePicture() }
        textView_phone.setOnClickListener { v -> onClickPhone() }
        imageButton_sms.setOnClickListener { v -> onClickSms() }
        imageButton_email.setOnClickListener { v -> onClickEmail() }
        imageButton_map.setOnClickListener { v -> onClickMap() }
        button_additInfo.setOnClickListener { v -> onClickAdditInfo() }

        var extras: Bundle = intent.extras!! // We get the extras sent from the previous activity

        lostUser = extras.getSerializable("lostUser") as BELostUser
        loggedUser = extras.getSerializable("loggedUser") as BEPrivilegedUser
        Toast.makeText(this, "User name is: ${loggedUser.username}", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "User role is: ${loggedUser.role}", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "LostUser ID is: ${lostUserId}", Toast.LENGTH_SHORT).show()

        if (loggedUser.role != "police" && loggedUser.role != "doctor") button_additInfo.setVisibility(
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

    private fun onClickProfilePicture() {
        if (loggedUser.role.equals("police")) {
            val intent = Intent(this, CameraActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
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
        val intent = Intent(this, AdditionalInfoActivity::class.java)
        intent.putExtra("lostUser", lostUser)
        intent.putExtra("loggedUser", loggedUser)
        startActivity(intent)
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (!isPermissionGiven())
            Toast.makeText(this, "You dont have permission for using GPS", Toast.LENGTH_SHORT)
                .show()

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        //handle cases: location can be null
        if (location != null) {
            currentLocationLat = location.latitude
            currentLocationLon = location.longitude
        }
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

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) { //Method that will check that the CameraActivity will return a picture and assign it to our friend as well as display it in our DetailsActivity
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                var newPicture = data?.extras?.getSerializable("newPicture") as File
                if (newPicture != null) {
                    imageButton_profilePicture.setImageDrawable(Drawable.createFromPath(newPicture?.absolutePath))
                    //lostUser.image = newPicture
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        var item: MenuItem = menu!!.findItem(R.id.action_newUser)
        if(loggedUser.role=="doctor" || loggedUser.role=="normal") item.setVisible(false)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()

        when(id) {
            R.id.action_newUser -> {
                Toast.makeText(this, "Action NewUser selected", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, CreateAccountActivity::class.java)
                intent.putExtra("loggedUser", loggedUser)
                startActivity(intent)
                true
            }
            R.id.action_myProfile -> {
                Toast.makeText(this, "Action MyProfile selected", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ProfileActivity::class.java)
                intent.putExtra("loggedUser", loggedUser)
                startActivity(intent)
                true
            }
            R.id.action_about -> {
                Toast.makeText(this, "Action About selected", Toast.LENGTH_SHORT).show()
                //val intent = Intent(this, AboutUsActivity::class.java)
                //startActivity(intent)
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}