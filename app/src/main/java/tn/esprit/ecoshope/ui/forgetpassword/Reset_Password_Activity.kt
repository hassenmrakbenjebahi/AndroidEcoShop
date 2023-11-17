package tn.esprit.ecoshope.ui.forgetpassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.ecoshope.MainActivity
import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.databinding.ActivityResetPasswordBinding
import tn.esprit.ecoshope.util.retrofitUser.ApiResponse
import tn.esprit.ecoshope.util.ClientObject


class Reset_Password_Activity : AppCompatActivity() {

    var jwtToken4: String? = null

    private lateinit var binding: ActivityResetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dialog = AlertDialog.Builder(this)
            .setView(R.layout.loading_item) // Créez un layout XML avec une ProgressBar dedans
            .setCancelable(false) // Empêche la fermeture du dialog lors de l'appui sur l'écran
            .create()

        jwtToken4= intent.getStringExtra("JWT_TOKEN2")



        binding.subBtn.setOnClickListener {
            val apiInterface = ClientObject.create()
            val pass = binding.passwordd.text.toString().trim()
            val cpass = binding.confirmpassword.text.toString().trim()
            dialog.show()




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
                        dialog.dismiss()
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