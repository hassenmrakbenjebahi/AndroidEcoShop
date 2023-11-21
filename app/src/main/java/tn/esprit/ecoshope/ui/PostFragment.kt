package tn.esprit.ecoshope.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.databinding.FragmentPostBinding
import tn.esprit.ecoshope.model.Post

import tn.esprit.ecoshope.ui.adapter.PostAdapter
import tn.esprit.ecoshope.ui.fragment.AddPostFragment
import tn.esprit.ecoshope.util.ServiceBuilder
import tn.esprit.ecoshope.util.post.PostService

class PostFragment: Fragment() {
  private lateinit var binding: FragmentPostBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {


        binding = FragmentPostBinding.inflate(layoutInflater)
        val postservice=ServiceBuilder.buildService(PostService::class.java)
        val fragmentmanager=requireFragmentManager();
        postservice.getPost().enqueue(object :Callback<List<Post>>{
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    val ListPost = response.body()!!
                    binding.rvPost.adapter = PostAdapter(ListPost,fragmentmanager)

                    Log.d("Post","jawek behi")

                } else {
                    // Gestion des réponses d'erreur
                    Log.e("Request Error", "Request failed with code: ")
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.e("Network Error", t.toString())

            }

        })


        binding.fab.setOnClickListener {
          fragmentmanager.beginTransaction().replace(R.id.fragment_container,AddPostFragment()).addToBackStack(null).commit()
        }
        binding.rvPost.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        return binding.root

    }



}