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
import tn.esprit.ecoshope.model.Comment

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

        val comments = listOf(
            Comment("1", "User1", "Great post!", "2023-11-12"),
            Comment("2", "User2", "Nice content", "2023-11-13")
        )

        // Sample likes
        val likes = listOf("User3", "User4")
        return mutableListOf(
            Post("",  "hassen mrakben","don't buy this product is dangers for arabe people ","10/10/10", comments,likes),
            Post("",  "badis aloui","don't buy this product is dangers for arabe people ","10/10/10", comments,likes)

        )

    }
}