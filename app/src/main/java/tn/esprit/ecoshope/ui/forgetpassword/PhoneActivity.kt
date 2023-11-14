package tn.esprit.ecoshope.ui.forgetpassword


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast

import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response




import tn.esprit.ecoshope.databinding.ActivityPhoneBinding

import tn.esprit.ecoshope.util.retrofitUser.Api
import tn.esprit.ecoshope.util.retrofitUser.ApiResponse


class PhoneActivity : AppCompatActivity() {


    var jwtToken: String? = null


    private lateinit var binding: ActivityPhoneBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPhoneBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.textphone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val phoneee = binding.textphone.text
                if (!phoneee?.startsWith("+")!!){
                    Snackbar.make(binding.root,
                        "The phone number must start with + country code",
                        Snackbar.LENGTH_LONG)
                        .show()
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        binding.buttonsub.setOnClickListener {
            val apiInterface = Api.create()

            val phone = binding.textphone.text.toString().trim()

            if (phone.isEmpty()){
                Snackbar.make(binding.root,
                    "You have some errors in your inputs!",
                    Snackbar.LENGTH_LONG)
                    .show()
            }
            apiInterface.forgetpassword(phone)
                .enqueue(object :Callback<ApiResponse>{
                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                        if (response.isSuccessful){
                            jwtToken = response.body()!!.token
                            Toast.makeText(
                                this@PhoneActivity,
                                "code send ",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this@PhoneActivity, CodeActivity::class.java)
                            intent.putExtra("JWT_TOKEN",jwtToken)
                            startActivity(intent)


                        }else{
                            Toast.makeText(
                                this@PhoneActivity,
                                "Error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        Toast.makeText(
                            this@PhoneActivity,
                            "Error, Please try later",
                            Toast.LENGTH_SHORT
                        ).show()
                    }


                })

        }
    }





}