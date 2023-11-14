package tn.esprit.ecoshope

import android.app.Activity
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
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.ecoshope.databinding.ActivityMainBinding
import tn.esprit.ecoshope.model.user.User
import tn.esprit.ecoshope.ui.Home.HomeActivity
import tn.esprit.ecoshope.ui.Home.RegisterActivity
import tn.esprit.ecoshope.ui.forgetpassword.PhoneActivity
import tn.esprit.ecoshope.util.retrofitUser.Api
import kotlin.math.log

class MainActivity : AppCompatActivity() {


    private var cancellationSignal: CancellationSignal?=null
    private val authenticationCollback: BiometricPrompt.AuthenticationCallback
        get() =
            @RequiresApi(Build.VERSION_CODES.P)
            object : BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errorCode, errString)
                    notifyuser("Authentication error:$errString ")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    notifyuser("Authentication success!!")
                }

            }

    private fun notifyuser(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkBiometricSupport()
        biometrique()

            binding.forgetpass.setOnClickListener {
                startActivity(Intent(this,PhoneActivity::class.java))
            }



        var fade_in = AnimationUtils.loadAnimation(this,R.anim.fade_in)
        var bottom_down =AnimationUtils.loadAnimation(this,R.anim.bottom_down)


        binding.toplinearLayout.animation =bottom_down

        var handler = Handler()
        var runnable = Runnable {
            binding.cardView.animation = fade_in
            binding.cardView2.animation = fade_in
            binding.cardView3.animation=fade_in
            binding.textView.animation=fade_in
            binding.forgetpass.animation =fade_in
            binding.registerLayout.animation=fade_in
        }
        handler.postDelayed(runnable,1000)

        binding.emaill.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.textemail.error = null
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.passwordd.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.textpassword.error=null
            }
            override fun afterTextChanged(s: Editable?) {}
        })


        binding.buttonLogin.setOnClickListener {
            val email = binding.emaill.text.toString().trim()
            val password = binding.passwordd.text.toString().trim()
            val apiInterface = Api.create()


            if (email.isEmpty() && password.isEmpty()){
                binding.textemail.error = "Must not be empty"
                binding.textpassword.error="Must not be empty"
                Snackbar.make(binding.root,"You have some errors in your inputs!", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if(email.isEmpty()) {
                binding.textemail.error = "Must not be empty"
                Snackbar.make(binding.root,"You have some errors in your inputs!", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (password.isEmpty()){
                binding.textpassword.error="Must not be empty"
                Snackbar.make(binding.root,"You have some errors in your inputs!", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            apiInterface.userlogin(email, password)
                .enqueue(object : Callback<User>{
                    override fun onResponse(call: Call<User>, response: Response<User>)
                    {
                        if (response.isSuccessful) {
                            val user = response.body()
                            if (user != null) {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Login Success",
                                    Toast.LENGTH_SHORT
                                ).show()
                                startActivity(Intent(applicationContext, HomeActivity::class.java))
                            } else {
                                Toast.makeText(
                                    this@MainActivity,
                                    "User not found",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }else{
                            Log.e("LoginError", "Response code: ${response.code()}")
                            val errorBody = response.errorBody()?.string()
                            Log.e("LoginError", "Error body: $errorBody")
                        }
                    }
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "LoginError", Toast.LENGTH_SHORT).show()
                    }
                })
        }
        binding.registerLayout.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }

    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun biometrique() {
        val biometricPrompt=BiometricPrompt.Builder(this)
            .setTitle("Authentication")
            .setSubtitle("Authentication is required")
            .setDescription("this app uses fingerprint protection to keep your data secure")
            .setNegativeButton("cancel",this.mainExecutor,DialogInterface.OnClickListener { dialog, which ->
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
    }

    private fun checkBiometricSupport():Boolean {
        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE)as KeyguardManager
        if (!keyguardManager.isKeyguardSecure){
            notifyuser("Fingerprint authentication has not been enabled in settings")
            return false
        }
        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.USE_BIOMETRIC)!=PackageManager.PERMISSION_GRANTED){
            notifyuser("Fingerprint authentication permission is not enabled")
            return false
        }
        return if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)){
            true
        }else true
    }

}


