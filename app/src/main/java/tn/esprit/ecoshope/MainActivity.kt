package tn.esprit.ecoshope

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tn.esprit.ecoshope.databinding.ActivityMainBinding
import tn.esprit.ecoshope.ui.PostFragment
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
   private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,PostFragment()).commit()
    }
}