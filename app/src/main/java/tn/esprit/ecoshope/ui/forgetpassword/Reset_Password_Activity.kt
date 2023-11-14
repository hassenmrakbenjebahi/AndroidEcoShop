package tn.esprit.ecoshope.ui.forgetpassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.ecoshope.MainActivity
import tn.esprit.ecoshope.databinding.ActivityResetPasswordBinding
import tn.esprit.ecoshope.model.user.User
import tn.esprit.ecoshope.util.retrofitUser.Api
import tn.esprit.ecoshope.util.retrofitUser.ApiResponse


class Reset_Password_Activity : AppCompatActivity() {

    var jwtToken4: String? = null

    private lateinit var binding: ActivityResetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        jwtToken4= intent.getStringExtra("JWT_TOKEN2")



        binding.subBtn.setOnClickListener {
            val apiInterface = Api.create()
            val pass = binding.passwordd.text.toString().trim()
            val cpass = binding.confirmpassword.text.toString().trim()




            if (pass.isEmpty() && cpass.isEmpty()){
                binding.textpassword.error="Must not be empty"
                binding.confpass.error="Must not be empty"
                Snackbar.make(binding.root,"You have some errors in your inputs!", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (pass.isEmpty()){
                binding.textpassword.error="Must not be empty"
                Snackbar.make(binding.root,"You have some errors in your inputs!", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (cpass.isEmpty()){
                binding.confpass.error="Must not be empty"
                Snackbar.make(binding.root,"You have some errors in your inputs!", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (pass != cpass){
                binding.textpassword.error="Must be the same password"
                binding.confpass.error="Must be the same password"
                return@setOnClickListener
            }
            apiInterface.resetpassword("$jwtToken4",pass,cpass)
                .enqueue(object :Callback<ApiResponse>{
                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                        if (response.isSuccessful){
                            Toast.makeText(
                                this@Reset_Password_Activity,
                                "Password updated successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(this@Reset_Password_Activity,MainActivity::class.java))

                        }else{

                            Toast.makeText(
                                this@Reset_Password_Activity,
                                "Error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        Log.d("ResetPassword", "JWT Token: ${t.message}")
                        Toast.makeText(
                            this@Reset_Password_Activity,
                            t.message,
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                })

        }

    }
}