package tn.esprit.ecoshope

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.ecoshope.databinding.ActivityMainBinding
import tn.esprit.ecoshope.model.user.User
import tn.esprit.ecoshope.ui.Home.HomeActivity
import tn.esprit.ecoshope.ui.Home.RegisterActivity
import tn.esprit.ecoshope.ui.forgetpassword.PhoneActivity
import tn.esprit.ecoshope.util.retrofitUser.ApiResponse
import tn.esprit.ecoshope.util.ClientObject
import tn.esprit.ecoshope.util.retrofitUser.Api
import tn.esprit.ecoshope.util.retrofitUser.ApiGoogle
import tn.esprit.ecoshope.util.retrofitUser.ProfileResponse
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    companion object {
        private const val RC_SIGN_IN = 9001
    }

    private lateinit var googleSignInClient:GoogleSignInClient



    private lateinit var binding: ActivityMainBinding
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)







        val dialog = AlertDialog.Builder(this)
            .setView(R.layout.loading_item) // Créez un layout XML avec une ProgressBar dedans
            .setCancelable(false) // Empêche la fermeture du dialog lors de l'appui sur l'écran
            .create()



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
            val apiInterface = ClientObject.buildService(Api::class.java)
            dialog.show()


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
                    .enqueue(object : Callback<ApiResponse> {
                        @SuppressLint("SuspiciousIndentation")
                        override fun onResponse(
                            call: Call<ApiResponse>,
                            response: Response<ApiResponse>
                        ) {
                            dialog.dismiss()
                            if (response.isSuccessful) {
                                val jwtToken = response.body()?.token
                                apiInterface.getUser("$jwtToken")
                                    .enqueue(object : Callback<ProfileResponse> {
                                        override fun onResponse(
                                            call: Call<ProfileResponse>,
                                            response: Response<ProfileResponse>
                                        ) {
                                            if (response.isSuccessful) {


                                                val sharedPreferences =
                                                    getSharedPreferences("MyApp", MODE_PRIVATE)
                                                with(sharedPreferences.edit()) {
                                                    putString("USER_ID",response.body()!!._id)
                                                    putString("USER_NAME", response.body()!!.name)
                                                    putString("USER_EMAIL", response.body()!!.email)
                                                    putString("PHONE_USER", response.body()!!.phone)


                                                    putString(
                                                        "USER_PHOTO_URL",
                                                        response.body()?.Image
                                                    )
                                                    putString("TOKEN", jwtToken)
                                                    apply()
                                                }

                                                Toast.makeText(
                                                    this@MainActivity,
                                                    "Login Success",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                startActivity(
                                                    Intent(
                                                        this@MainActivity,
                                                        HomeActivity::class.java
                                                    )
                                                )
                                                finish()
                                            } else {
                                                Toast.makeText(
                                                    this@MainActivity,
                                                    "Error",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }


                                        }

                                        override fun onFailure(
                                            call: Call<ProfileResponse>,
                                            t: Throwable
                                        ) {
                                            Toast.makeText(
                                                this@MainActivity,
                                                "U have some error try again",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                    })

                            } else {
                                Log.e("LoginError", "Response code: ${response.code()}")
                                val errorBody = response.errorBody()?.string()
                                Log.e("LoginError", "Error body: $errorBody")
                            }
                        }

                        override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                            Toast.makeText(this@MainActivity, "LoginError", Toast.LENGTH_SHORT)
                                .show()
                        }
                    })



        }
        binding.registerLayout.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }


        binding.cardView2.setOnClickListener {
            signIn()
        }

    }

    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val name = account.displayName
                val email = account.email
                val photoUrl = account.photoUrl.toString()
                val sharedPreferences =
                    getSharedPreferences("MyApp2", MODE_PRIVATE)
                with(sharedPreferences.edit()) {
                    putString("USER_NAME2", name)
                    putString("USER_EMAIL2", email)
                    putString("USER_PHOTO_URL2", photoUrl)

                    apply()
                }


                AuthWithGoogle(account.idToken!!)

            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun AuthWithGoogle(idToken: String) {
        val apiInterface = ClientObject.buildService(Api::class.java)
        apiInterface.google("$idToken")
            .enqueue(object :Callback<ApiGoogle>{
                override fun onResponse(call: Call<ApiGoogle>, response: Response<ApiGoogle>) {
                    if (response.isSuccessful){

                        Toast.makeText(
                            this@MainActivity,
                            "Register Success",
                            Toast.LENGTH_SHORT
                        ).show()

                        startActivity(Intent(this@MainActivity,HomeActivity::class.java))
                    }else{
                        val errorBody = response.errorBody()?.string()
                        Log.e("AuthWithGoogle", "Error123: $errorBody")
                        Toast.makeText(
                            this@MainActivity,
                            "U have some error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                override fun onFailure(call: Call<ApiGoogle>, t: Throwable) {
                    Toast.makeText(
                        this@MainActivity,
                        "Registe failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}


