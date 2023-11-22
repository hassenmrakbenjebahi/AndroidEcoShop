package tn.esprit.ecoshope.ui.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tn.esprit.ecoshope.databinding.FragmentAddPostBinding
import tn.esprit.ecoshope.databinding.FragmentPostDetailBinding

class AddPost: AppCompatActivity()  {
    private lateinit var binding: FragmentAddPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}