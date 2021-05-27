package com.easv.takemehomeapp.Activities

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*
import com.easv.takemehomeapp.Data.IUserDAO
import com.easv.takemehomeapp.Data.UserDAO_Impl
import com.easv.takemehomeapp.Model.BELostUser
import com.easv.takemehomeapp.Model.BEPrivilegedUser
import com.easv.takemehomeapp.R
import kotlinx.android.synthetic.main.activity_code_scanner.*

private const val CAMERA_REQUEST_CODE = 101

class CodeScannerActivity : AppCompatActivity() {

    private lateinit var codeScanner: CodeScanner
    private lateinit var loggedUser: BEPrivilegedUser
    private lateinit var lostUser: BELostUser
    private lateinit var lostUserDB: IUserDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_scanner)

        lostUserDB = UserDAO_Impl(this)

        button_go.setVisibility(View.GONE)
        button_go.setOnClickListener { v -> onClickGo() }

        var extras: Bundle = intent.extras!!
        loggedUser = extras.getSerializable("loggedUser") as BEPrivilegedUser

        setUpPermissions()
        codeScanner()
    }

    fun onClickGo() {
        var intent = Intent(this, InfoActivity::class.java)
        intent.putExtra("loggedUser", loggedUser)
        intent.putExtra("lostUser", lostUser)
        startActivity(intent)
        finish()
    }

    private fun codeScanner() {
        codeScanner = CodeScanner(this, scanner_view)
        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS
            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                runOnUiThread {
                    var id = Integer.parseInt(it.text)
                    lostUser = lostUserDB.getLostUserById(id)
                    if (lostUser.id > 0) {
                        scan_label.setText("Scan finished.")
                        button_go.setVisibility(View.VISIBLE)
                    } else {
                        scan_label.text = "Invalid code. Try again."
                    }
                }
            }

            errorCallback = ErrorCallback {
                runOnUiThread {
                    Log.e("Main", "Camera initialization error: ${it.message}")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    private fun setUpPermissions() {
        val permission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.CAMERA
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        this,
                        "Please, add camera permission to use the scanner. Close and reopen this app to accept the permission.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    //successful
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        var item: MenuItem = menu!!.findItem(R.id.action_newUser)
        if (loggedUser.role == "doctor" || loggedUser.role == "normal") item.setVisible(false)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()

        when (id) {
            R.id.action_newUser -> {
                Toast.makeText(this, "Action NewUser selected", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, CreateLostUserAccountActivity::class.java)
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
                val intent = Intent(this, AboutUsActivity::class.java)
                startActivity(intent)
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}