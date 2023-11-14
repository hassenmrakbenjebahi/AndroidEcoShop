package tn.esprit.ecoshope.ui.Home

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import okhttp3.MultipartBody
import retrofit2.Call
import tn.esprit.ecoshope.MainActivity
import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.databinding.ActivityMainBinding
import tn.esprit.ecoshope.databinding.ActivityRegisterBinding
import tn.esprit.ecoshope.model.user.User
import tn.esprit.ecoshope.util.retrofitUser.Api
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private var imageUri: Uri? = null
    private lateinit var binding:ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.imageregister.setOnClickListener {
            openImageChooser()
        }


        binding.backLogin.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        var fade_in = AnimationUtils.loadAnimation(this,R.anim.fade_in)
        var bottom_down = AnimationUtils.loadAnimation(this,R.anim.bottom_down)


        binding.toplinearLayout2.animation =bottom_down



        var handler = Handler()
        var runnable = Runnable {
            binding.cardView2.animation = fade_in
            binding.cardView3.animation = fade_in

            binding.textView.animation=fade_in
            binding.backLogin.animation =fade_in
            binding.buttonRegister.animation=fade_in
            binding.textView2.animation=fade_in
        }
        handler.postDelayed(runnable,1000)




        binding.username.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.usernamee.error=null
                if (binding.username.length() < 6){
                    binding.usernamee.error="Must be a at least 6 characters!"
                }
            }
        })

        binding.emaill.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.textemail.error=null }
        })

        binding.passwordd.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.textpassword.error=null
            }
        })

        binding.confirmpassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.confpass.error=null
            }
        })
        binding.phone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val phoneee = binding.phone.text
                if (!phoneee?.startsWith("+")!!){
                    binding.phoneee.error="The phone number must start with + country code"
                }else{
                    binding.phoneee.error=null
                }
            }
        })






        binding.buttonRegister.setOnClickListener {
            val apiInterface = Api.create()
            val fullname = binding.username.text.toString()
            val email = binding.emaill.text.toString().trim()
            val pass = binding.passwordd.text.toString().trim()
            val cpass = binding.confirmpassword.text.toString().trim()
            val phone = binding.phone.text.toString().trim()
            val image = prepareImagePart(imageUri!!)

            if (fullname.isEmpty() && email.isEmpty() && pass.isEmpty() && cpass.isEmpty() && phone.isEmpty()){
                binding.usernamee.error="Must not be empty"
                binding.textemail.error="Must not be empty"
                binding.textpassword.error="Must not be empty"
                binding.confpass.error="Must not be empty"
                binding.phoneee.error="Must not be empty"
                Snackbar.make(binding.root,"You have some errors in your inputs!", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (fullname.isEmpty()){
                binding.usernamee.error="Must not be empty"
                Snackbar.make(binding.root,"You have some errors in your inputs!", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (email.isEmpty()){
                binding.textemail.error="Must not be empty"
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
            if (phone.isEmpty()){
                binding.phoneee.error="Must not be empty"
                Snackbar.make(binding.root,"You have some errors in your inputs!", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (pass != cpass){
                binding.textpassword.error="Must be the same password"
                binding.confpass.error="Must be the same password"
                return@setOnClickListener
            }
            if (imageUri == null) {
                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val fullnameReq = fullname.toRequestBody("text/plain".toMediaTypeOrNull())
            val emailReq = email.toRequestBody("text/plain".toMediaTypeOrNull())
            val passwordReq = pass.toRequestBody("text/plain".toMediaTypeOrNull())
            val confirmPasswordReq = cpass.toRequestBody("text/plain".toMediaTypeOrNull())
            val phoneReq = phone.toRequestBody("text/plain".toMediaTypeOrNull())

            apiInterface.usersignup(fullnameReq ,emailReq, passwordReq,confirmPasswordReq,phoneReq,image)
                .enqueue(object :Callback<User>{
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful){
                            Toast.makeText(
                                this@RegisterActivity,
                                "Register Success",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(applicationContext,MainActivity::class.java))
                        }else{
                            Toast.makeText(
                                this@RegisterActivity,
                                "U have some error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Registe failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
    }


    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type="image/*"
            val mimetypes = arrayOf("image/jpeg","image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES,mimetypes)

            startActivityForResult(it,REQUEST_CODE_IMAGE)
        }
    }
    companion object {
       const val REQUEST_CODE_IMAGE = 101
    }


    fun prepareImagePart(imageUri: Uri): MultipartBody.Part {
        val inputStream = contentResolver.openInputStream(imageUri)
        val requestFile = inputStream?.readBytes()?.toRequestBody("image/jpeg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("Image", "image.jpg", requestFile!!)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = data?.data

            binding.imageregister.setImageURI(selectedImageUri)
            imageUri = selectedImageUri
        }
    }
}