package tn.esprit.ecoshope.ui.profile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.ecoshope.MainActivity
import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.databinding.ActivityPasswordBinding
import tn.esprit.ecoshope.ui.forgetpassword.PhoneActivity
import tn.esprit.ecoshope.util.retrofitUser.ApiResponse
import tn.esprit.ecoshope.util.ClientObject

class PasswordActivity : AppCompatActivity() {
    private fun notifyuser(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private lateinit var binding:ActivityPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dialog = AlertDialog.Builder(this)
            .setView(R.layout.loading_item) // Créez un layout XML avec une ProgressBar dedans
            .setCancelable(false) // Empêche la fermeture du dialog lors de l'appui sur l'écran
            .create()


        val sharedPreferences = getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        val jwtToken = sharedPreferences.getString("TOKEN", "")




        binding.back.setOnClickListener {
            startActivity(Intent(this,SettingsActivity::class.java))
        }
        binding.FrogotPassword.setOnClickListener {
            startActivity(Intent(this,PhoneActivity::class.java))
        }

        binding.buttonChange.setOnClickListener {
            val current = binding.currentpasswordd.text.toString()
            val password = binding.passwordd.text.toString()
            val Cpassword = binding.confirmpassword.text.toString()
            val apiInterface = ClientObject.create()
            dialog.show()

            if (current.isEmpty() && password.isEmpty() && Cpassword.isEmpty()) {
                binding.textcurrentpassword.error = "Must not be empty"
                binding.textpassword.error = "Must not be empty"
                binding.confpass.error = "Must not be empty"
                Snackbar.make(
                    binding.root,
                    "You have some errors in your inputs!",
                    Snackbar.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            if (current.isEmpty()) {
                binding.textcurrentpassword.error = "Must not be empty"
                Snackbar.make(
                    binding.root,
                    "You have some errors in your inputs!",
                    Snackbar.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.textpassword.error = "Must not be empty"
                Snackbar.make(
                    binding.root,
                    "You have some errors in your inputs!",
                    Snackbar.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            if (Cpassword.isEmpty()) {
                binding.confpass.error = "Must not be empty"
                Snackbar.make(
                    binding.root,
                    "You have some errors in your inputs!",
                    Snackbar.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            if (password != Cpassword) {
                binding.textpassword.error = "Must be the same password"
                binding.confpass.error = "Must be the same password"
                return@setOnClickListener
            }


                apiInterface.updatepassword("$jwtToken", current, password, Cpassword)
                    .enqueue(object : Callback<ApiResponse> {
                        override fun onResponse(
                            call: Call<ApiResponse>,
                            response: Response<ApiResponse>
                        ) {
                            dialog.dismiss()
                            if (response.isSuccessful) {
                                Toast.makeText(
                                    this@PasswordActivity,
                                    "Password updated successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val editor = sharedPreferences.edit()
                                editor.remove("JWT_TOKEN")
                                editor.apply()
                                val intent = Intent(this@PasswordActivity, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)

                            } else {
                                Toast.makeText(
                                    this@PasswordActivity,
                                    "Error updating",
                                    Toast.LENGTH_SHORT
                                ).show()


                            }
                        }

                        override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                            Toast.makeText(
                                this@PasswordActivity,
                                "Error123: ${t.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    })


        }

    }

}