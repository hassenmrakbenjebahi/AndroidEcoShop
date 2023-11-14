package tn.esprit.ecoshope.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.databinding.ActivityPersonalBinding
import tn.esprit.ecoshope.databinding.ActivityRegisterBinding

class PersonalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPersonalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}