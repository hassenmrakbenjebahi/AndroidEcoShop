package tn.esprit.ecoshope.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Personal.setOnClickListener {
            startActivity(Intent(this,PersonalActivity::class.java))
        }

        binding.Password.setOnClickListener {
            startActivity(Intent(this,PasswordActivity::class.java))
        }
        binding.AboutUs.setOnClickListener {
            startActivity(Intent(this,AboutActivity::class.java))
        }

    }
}