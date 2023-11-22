package tn.esprit.ecoshope.ui.forgetpassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.databinding.ActivityCode2Binding
import tn.esprit.ecoshope.util.retrofitUser.ApiResponse
import tn.esprit.ecoshope.util.ClientObject
import tn.esprit.ecoshope.util.retrofitUser.Api

class CodeActivity : AppCompatActivity() {
    private var countdownTimer: CountDownTimer? = null
    private var timeRemaining: Long = 0

    var jwtToken2: String? = null
    var jwtToken3: String? = null

    private lateinit var binding: ActivityCode2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCode2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setupOtpInputs()

        jwtToken2= intent.getStringExtra("JWT_TOKEN")

        binding.resendbtn.setOnClickListener {
            startCountdown()
        }
        binding.verifyBtn.setOnClickListener {
            val apiInterface = ClientObject.buildService(Api::class.java)
            val otp1 = binding.otpET1.text.toString()
            val otp2 = binding.otpET2.text.toString()
            val otp3 = binding.otpET3.text.toString()
            val otp4 = binding.otpET4.text.toString()
            val codeotp = otp1 + otp2+otp3 + otp4


            if (otp1.isEmpty() && otp2.isEmpty() && otp3.isEmpty() && otp4.isEmpty()){
                Snackbar.make(binding.root,
                    "You have some errors in your inputs!",
                    Snackbar.LENGTH_LONG)
                    .show()

            }
            apiInterface.otp("$jwtToken2",codeotp)
                .enqueue(object : Callback<ApiResponse> {
                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                        if (response.isSuccessful){
                            jwtToken3 = response.body()!!.token
                           // Log.d("CodeActivity", "JWT Token: $jwtToken3")
                            val intent2 = Intent(this@CodeActivity, Reset_Password_Activity::class.java)
                            intent2.putExtra("JWT_TOKEN2",jwtToken3)
                            startActivity(intent2)

                            Toast.makeText(
                                this@CodeActivity,
                                "Code Correct",
                                Toast.LENGTH_SHORT
                            ).show()
                           // startActivity(Intent(this@CodeActivity,Reset_Password_Activity::class.java)) hedhi li 7chethouli
                        }else{
                            Toast.makeText(
                                this@CodeActivity,
                                "Incorrect code",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        Toast.makeText(
                            this@CodeActivity,
                            "U have some error try again",
                            Toast.LENGTH_SHORT
                        ).show()
                    }


                })

        }

    }



    private fun startCountdown() {
        timeRemaining = 60000
        countdownTimer?.cancel()
        binding.resendbtn.isEnabled = false

        countdownTimer = object : CountDownTimer(timeRemaining, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Mettez à jour le texte du bouton avec le temps restant
                binding.resendbtn.text = "Resend Code (${millisUntilFinished / 1000}s)"
                timeRemaining = millisUntilFinished
                binding.resendbtn.setTextColor(ContextCompat.getColor(this@CodeActivity, R.color.gris))

            }

            override fun onFinish() {
                binding.resendbtn.isEnabled = true
                binding.resendbtn.text = "Resend Code"
                binding.resendbtn.setTextColor(ContextCompat.getColor(this@CodeActivity, R.color.rose))

            }
        }.start()
    }
    private fun setupOtpInputs() {
        val otp= listOf(binding.otpET1,binding.otpET2,binding.otpET3,binding.otpET4)
        otp.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (s!= null && s.length ==1 && index < otp.size -1){
                        otp[index+1].requestFocus()
                    }
                }


            })
        }
    }

}