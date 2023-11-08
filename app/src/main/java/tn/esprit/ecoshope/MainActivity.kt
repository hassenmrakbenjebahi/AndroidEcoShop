package tn.esprit.ecoshope

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.ecoshope.databinding.ActivityMainBinding
import tn.esprit.ecoshope.model.user.User
import tn.esprit.ecoshope.ui.Home.HomeActivity
import tn.esprit.ecoshope.util.retrofitUser.Api
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

            var fade_in = AnimationUtils.loadAnimation(this,R.anim.fade_in)
        var bottom_down =AnimationUtils.loadAnimation(this,R.anim.bottom_down)


        binding.toplinearLayout.animation =bottom_down

        var handler = Handler()
        var runnable = Runnable {
            binding.cardView.animation = fade_in
            binding.cardView2.animation = fade_in
            binding.cardView3.animation=fade_in
            binding.textView.animation=fade_in
            binding.textView2.animation =fade_in
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

    }

}


