package com.rajit.contacts_content_provider

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.rajit.contacts_content_provider.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mAdapter by lazy { ContactRvAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.songsRv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            setHasFixedSize(true)
            adapter = mAdapter
        }

        if(hasPermission()) {
            val contacts = ContactUtil.importContact(this@MainActivity)
            mAdapter.setList(contacts)
        } else {
            requestPermission()
            showMandatoryPermissionDialog()
        }

    }

    @SuppressLint("InlinedApi")
    private fun hasPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this@MainActivity,
            Constants.READ_CONTACT_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this@MainActivity,
            arrayOf(Constants.READ_CONTACT_PERMISSION),
            Constants.PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted; proceed with accessing MediaStore.Audio
                Constants.permissionGrantedToast(this@MainActivity)
                mAdapter.setList(ContactUtil.importContact(this@MainActivity))
            } else {
                // Permission denied; handle the case
                Constants.permissionDeniedToast(this@MainActivity)
                showMandatoryPermissionDialog()
            }
        }
    }

    private fun showMandatoryPermissionDialog() {
        AlertDialog.Builder(this@MainActivity)
            .setTitle("Mandatory Permission")
            .setMessage("READ CONTACTS PERMISSION is required to import all contact present in your phone.")
            .setCancelable(false)
            .setPositiveButton("GRANT") { dialog, _ ->
                dialog.dismiss()
                openAppSettingsPage()
            }
            .show()
    }

    private fun openAppSettingsPage() {
        // If user wants to grant permission from Mandatory Dialog,
        // we can help user navigate to permission page using this intent
        // This intent will take user to our application's settings page
        startActivity(
            Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", packageName, null)
            )
        )
    }

}