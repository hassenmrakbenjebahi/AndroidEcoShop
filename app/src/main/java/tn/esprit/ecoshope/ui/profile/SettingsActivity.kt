package tn.esprit.ecoshope.ui.profile

import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.ecoshope.MainActivity
import tn.esprit.ecoshope.databinding.ActivitySettingsBinding
import tn.esprit.ecoshope.util.ClientObject
import tn.esprit.ecoshope.util.retrofitUser.Api
import tn.esprit.ecoshope.util.retrofitUser.ProfileResponse

class SettingsActivity : AppCompatActivity() {


    private var cancellationSignal: CancellationSignal?=null
    private val authenticationCollback: BiometricPrompt.AuthenticationCallback
        get() =
            @RequiresApi(Build.VERSION_CODES.P)
            object : BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errorCode, errString)
                    notifyuser("Authentication error:$errString ")
                    finish()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    notifyuser("Authentication success!!")
                }

            }
    private fun notifyuser(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    private lateinit var binding: ActivitySettingsBinding
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            startActivity(Intent(applicationContext,Edit_Profile_Fragment::class.java))
        }
        val sharedPreferences = getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        val jwtToken = sharedPreferences.getString("TOKEN", "")

        binding.Personal.setOnClickListener {
            startActivity(Intent(this,PersonalActivity::class.java))
        }

        binding.Password.setOnClickListener {
            checkBiometricSupport()
            biometrique()
            startActivity(Intent(this,PasswordActivity::class.java))

        }
        binding.AboutUs.setOnClickListener {
            startActivity(Intent(this,AboutActivity::class.java))
        }
        binding.deleteaccount.setOnClickListener {
            checkBiometricSupport()
            biometrique()
            AlertDialog.Builder(this)
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account? This cannot be undone.")
                .setPositiveButton("Yes") { dialog, which ->
                    val apiInterface = ClientObject.buildService(Api::class.java)
                    apiInterface.deleteUser("$jwtToken")
                        .enqueue(object :Callback<ProfileResponse>{
                            override fun onResponse(
                                call: Call<ProfileResponse>,
                                response: Response<ProfileResponse>
                            ) {
                                if (response.isSuccessful){
                                    Toast.makeText(this@SettingsActivity, "Account deleted successful", Toast.LENGTH_SHORT).show()
                                    val editor = sharedPreferences.edit()
                                    editor.remove("JWT_TOKEN")
                                    editor.apply()
                                    val intent = Intent(this@SettingsActivity, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)
                                }else{
                                    Toast.makeText(this@SettingsActivity, "Error deleting Account", Toast.LENGTH_SHORT).show()

                                }
                            }

                            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                                Toast.makeText(this@SettingsActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                            }

                        })


                }
                    .setNegativeButton("No") { dialog, which ->

                    dialog.dismiss()
                }
                .show()
        }

    }
    @RequiresApi(Build.VERSION_CODES.P)
    private fun biometrique() {
        val biometricPrompt=BiometricPrompt.Builder(this)
            .setTitle("Authentication")
            .setSubtitle("Authentication is required")
            .setDescription("this app uses fingerprint protection to keep your data secure")
            .setNegativeButton("cancel",this.mainExecutor,
                DialogInterface.OnClickListener { dialog, which ->
                notifyuser("Authetication cancelled")
                finish()
            }).build()
        biometricPrompt.authenticate(getCancellationSignal(),mainExecutor,authenticationCollback)
    }

    private fun getCancellationSignal():CancellationSignal{
        cancellationSignal= CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            notifyuser("Authentication was cancelled by the user")

        }
        return cancellationSignal as CancellationSignal
        finish()
    }

    private fun checkBiometricSupport():Boolean {
        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE)as KeyguardManager
        if (!keyguardManager.isKeyguardSecure){
            notifyuser("Fingerprint authentication has not been enabled in settings")
            return false
        }
        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.USE_BIOMETRIC)!= PackageManager.PERMISSION_GRANTED){
            notifyuser("Fingerprint authentication permission is not enabled")
            return false
        }
        return if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)){
            true
        }else true
    }
}