package tn.esprit.ecoshope.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import tn.esprit.ecoshope.databinding.FragmentPostBinding
import tn.esprit.ecoshope.model.Post
import tn.esprit.ecoshope.ui.adapter.PostAdapter

class PostFragment: Fragment() {
  private lateinit var binding: FragmentPostBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPostBinding.inflate(layoutInflater)

        binding.rvPost.adapter = PostAdapter(getListPost())
        binding.rvPost.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        return binding.root
    }


    private fun getListPost() : MutableList<Post> {
        return mutableListOf(
            Post("hassen mrakben","don't buy this product is dangers for arabe people "),
            Post("badis aloui","don't buy this product is dangers for people ")

        )

    }
}