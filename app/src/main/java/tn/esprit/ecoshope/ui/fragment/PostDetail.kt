package tn.esprit.ecoshope.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.ecoshope.databinding.ActivityMainBinding
import tn.esprit.ecoshope.databinding.FragmentPostDetailBinding

class PostDetail: AppCompatActivity()  {
    private lateinit var binding: FragmentPostDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentPostDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}