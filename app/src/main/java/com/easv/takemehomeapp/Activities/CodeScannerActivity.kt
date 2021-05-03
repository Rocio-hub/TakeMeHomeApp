package com.easv.takemehomeapp.Activities

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*
import com.easv.takemehomeapp.R
import kotlinx.android.synthetic.main.activity_code_scanner.*

private const val CAMERA_REQUEST_CODE = 101

class CodeScannerActivity : AppCompatActivity() {

    private lateinit var codeScanner: CodeScanner
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_scanner)

        button_login.setVisibility(View.GONE)
        button_login.setOnClickListener { v -> onClickLogin() }

        setUpPermissions()
        codeScanner()
    }

    private fun onClickLogin() {
        /*val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent) */
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
                    textview.text = "Valid code. Please, log in to get access."
                    id = Integer.parseInt(it.text)
                    button_login.setVisibility(View.VISIBLE)
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
                CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray) {
        when(requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Please, add camera permission to use the scanner. Close and reopen this app to accept the permission.", Toast.LENGTH_SHORT).show()
                }
                else{
                    //successful
                }
            }
        }
    }
}