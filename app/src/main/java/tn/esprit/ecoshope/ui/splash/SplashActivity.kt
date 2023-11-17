package tn.esprit.ecoshope.ui.splash

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import tn.esprit.ecoshope.MainActivity
import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    companion object val CAMERA_PERMISSION_REQUEST_CODE = 123
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler().postDelayed({
            checkForPermission()
        },2000)
    }

    private fun checkForPermission() {
        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
            goToMainActivity()
        }else
            requestPermission()
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE)
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode==CAMERA_PERMISSION_REQUEST_CODE){
            if (grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                goToMainActivity()
            }else if (isUserPermanentDenied()){
                showGoToAppDialog()
            }else
                requestPermission()
        }
    }

    private fun showGoToAppDialog() {
        AlertDialog.Builder(this)
            .setTitle("Grant Permissions")
            .setMessage("We need camera permission to scan QR code, Go to app Settings and manage permission")
            .setPositiveButton("Grant"){dialog, which ->
                goToAppSettings()
            }
            .setNegativeButton("Cancel"){dialog, which ->
                Toast.makeText(this, "We need permission for camera", Toast.LENGTH_SHORT).show()
                finish()
            }.show()

    }

    private fun goToAppSettings() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package",packageName,null))
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun isUserPermanentDenied(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA).not()
        } else {
            return false
        }
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onRestart() {
        super.onRestart()
        checkForPermission()
    }
}