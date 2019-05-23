package ru.example.ivan.smssender.ui.screens.main


import android.Manifest
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import dagger.android.support.DaggerAppCompatActivity
import ru.example.ivan.smssender.R
import ru.example.ivan.smssender.utility.Constants
import javax.inject.Inject
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.example.ivan.smssender.utility.navigation.OnBackPressedListener


class MainActivity : DaggerAppCompatActivity() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showRequestPermissionRationale(true)

        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_messages -> {
                    Navigation.findNavController(this, R.id.fr_container).popBackStack(R.id.chainFragment, false)
                }
                R.id.action_groups -> {
                    var bundle = Bundle()
                    bundle.putBoolean(Constants.KEY_IS_SELECTION_FRAGMENT, false)
                    val navController = Navigation.findNavController(this, R.id.fr_container)
                    navController.popBackStack(R.id.chainFragment, false)
                    navController.navigate(R.id.groupFragment, bundle)
                }
                R.id.action_templates -> {
                    var bundle = Bundle()
                    bundle.putBoolean(Constants.KEY_IS_SELECTION_FRAGMENT, false)

                    val navController = Navigation.findNavController(this, R.id.fr_container)
                    navController.popBackStack(R.id.chainFragment, false)
                    navController.navigate(R.id.templateFragment, bundle)
                }
            }
            true
        }
    }

    private fun checkPermissions(): ArrayList<String> {
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

        return permissionArray
    }

    private fun requestPermisson(permissionArray: ArrayList<String>) {
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
        if (checkPermissions().isNullOrEmpty()) {
            return
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Предоставьте разрешения")
        builder.setMessage(Constants.DIALOG_RATIONALE_TEXT)

        builder.setPositiveButton(if (isFirst) {Constants.DIALOG_BUTTON_OK} else {Constants.DIALOG_BUTTON_PROVIDE}) { dialog, which ->
            requestPermisson(checkPermissions())
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

    override fun onBackPressed() {
        val curLabel = Navigation.findNavController(this, R.id.fr_container).currentDestination?.label.toString()
        if (curLabel == "GroupFragment" ||
            curLabel == "TemplateFragment") {
            val navHostFragment = supportFragmentManager.fragments.first() as? NavHostFragment
            val fragment = navHostFragment?.childFragmentManager?.fragments?.last()
            (fragment as? OnBackPressedListener)?.onBackPressed()
        } else {
            super.onBackPressed()
        }
    }

}

