package tn.esprit.ecoshope.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.databinding.ActivityPasswordBinding
import tn.esprit.ecoshope.databinding.ActivityRegisterBinding

class PasswordActivity : AppCompatActivity() {
    private lateinit var binding:ActivityPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}