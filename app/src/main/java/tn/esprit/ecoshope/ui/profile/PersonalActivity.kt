package tn.esprit.ecoshope.ui.profile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.ecoshope.MainActivity
import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.databinding.ActivityPersonalBinding
import tn.esprit.ecoshope.util.retrofitUser.ApiResponse
import tn.esprit.ecoshope.util.ClientObject

class PersonalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPersonalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dialog = AlertDialog.Builder(this)
            .setView(R.layout.loading_item) // Créez un layout XML avec une ProgressBar dedans
            .setCancelable(false) // Empêche la fermeture du dialog lors de l'appui sur l'écran
            .create()



        binding.back.setOnClickListener {
            startActivity(Intent(applicationContext,SettingsActivity::class.java))
        }

        val sharedPreferences = getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("USER_NAME", "Default Name")
        val userEmail = sharedPreferences.getString("USER_EMAIL", "Default Email")
        val userPhotoUrl = sharedPreferences.getString("USER_PHOTO_URL", "Default Email")
        val phone = sharedPreferences.getString("PHONE_USER","Default Phone")
        val jwtToken = sharedPreferences.getString("TOKEN", "")


        binding.username.setText(userName)
        binding.emaill.setText(userEmail)
        binding.phone.setText(phone)
        userPhotoUrl?.let { url ->
            Glide.with(this)
                .load(url)
                .error(R.drawable.outline_person_24)             // Replace with your error drawable
                .into(binding.imageView6)          // The ID of your ImageView
        }

        binding.buttonUpdate.setOnClickListener {
            val updatename= binding.username.text.toString().trim()
            val updateemail =binding.emaill.text.toString().trim()
            val updatephone= binding.phone.text.toString().trim()
            val apiInterface = ClientObject.create()
            dialog.show()

            if(updateemail.isEmpty()&&updatename.isEmpty()&&updatephone.isEmpty()){
                binding.usernamee.error = "Must not be empty"
                binding.textemail.error = "Must not be empty"
                binding.phoneee.error = "Must not be empty"
                Snackbar.make(
                    binding.root,
                    "You have some errors in your inputs!",
                    Snackbar.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            if (updateemail.isEmpty()){
                binding.textemail.error = "Must not be empty"

                Snackbar.make(
                    binding.root,
                    "You have some errors in your inputs!",
                    Snackbar.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            if (updatename.isEmpty()){
                binding.usernamee.error = "Must not be empty"
                Snackbar.make(
                    binding.root,
                    "You have some errors in your inputs!",
                    Snackbar.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            if(updatephone.isEmpty()){
                binding.phoneee.error = "Must not be empty"
                Snackbar.make(
                    binding.root,
                    "You have some errors in your inputs!",
                    Snackbar.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }



                apiInterface.updateUser("$jwtToken", updatename, updateemail, updatephone)
                    .enqueue(object : Callback<ApiResponse> {
                        override fun onResponse(
                            call: Call<ApiResponse>,
                            response: Response<ApiResponse>
                        ) {
                            dialog.dismiss()
                            if (response.isSuccessful) {
                                Toast.makeText(
                                    this@PersonalActivity,
                                    "Update successful",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val editor = sharedPreferences.edit()
                                editor.remove("JWT_TOKEN")
                                editor.apply()
                                val intent = Intent(this@PersonalActivity, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            } else {

                                Toast.makeText(
                                    this@PersonalActivity,
                                    "Update failed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                            Toast.makeText(
                                this@PersonalActivity,
                                "Error: ${t.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    })


        }


    }
}