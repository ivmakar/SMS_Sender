package ru.example.ivan.smssender.ui.screens.main


import android.Manifest
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import dagger.android.support.DaggerAppCompatActivity
import ru.example.ivan.smssender.R
import ru.example.ivan.smssender.utility.Constants
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showRequestPermissionRationale(true)

        setContentView(R.layout.activity_main)
    }

    private fun checkPermissions() {
        var permissionArray = ArrayList<String>()
        //READ_CONTACTS
        val readContactsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
        if (readContactsPermission == PackageManager.PERMISSION_DENIED) {
            permissionArray.add(Manifest.permission.READ_CONTACTS)
        }
        //SEND_SMS
        val sendSmsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
        if (sendSmsPermission == PackageManager.PERMISSION_DENIED) {
            permissionArray.add(Manifest.permission.SEND_SMS)
        }
        //READ_PHONE_STATE
        val readPhoneStatePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
        if (readPhoneStatePermission == PackageManager.PERMISSION_DENIED) {
            permissionArray.add(Manifest.permission.READ_PHONE_STATE)
        }

        if (permissionArray.isNotEmpty()) {
            ActivityCompat.requestPermissions(this,
                permissionArray.toTypedArray(),
                Constants.REQUEST_CODE_PERMISSION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        for ((i, perm) in permissions.withIndex()) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                showRequestPermissionRationale(false)
            }
        }
    }

    private fun showRequestPermissionRationale(isFirst: Boolean) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Предоставьте разрешения")
        builder.setMessage(Constants.DIALOG_RATIONALE_TEXT)

        builder.setPositiveButton(if (isFirst) {Constants.DIALOG_BUTTON_OK} else {Constants.DIALOG_BUTTON_PROVIDE}) { dialog, which ->
            checkPermissions()
        }

        if (!isFirst) {
            builder.setNegativeButton(Constants.DIALOG_BUTTON_EXIT) { dialog, which ->
                exit()
            }
        }
        builder.show()
    }

    private fun exit() {
        finish()
    }

}

