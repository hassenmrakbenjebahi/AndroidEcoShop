package tn.esprit.ecoshope.ui.HomeActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.databinding.FragmentPostBinding
import tn.esprit.ecoshope.model.Post.Post
import tn.esprit.ecoshope.ui.adapter.PostAdapter
import tn.esprit.ecoshope.ui.fragment.AddPost
import tn.esprit.ecoshope.ui.fragment.AddPostFragment
import tn.esprit.ecoshope.util.ClientObject
import tn.esprit.ecoshope.util.post.PostService

class HomeFragment : Fragment() {

    companion object{
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
    private lateinit var binding: FragmentPostBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val sharedPreferences = requireActivity().getSharedPreferences("MyApp", Context.MODE_PRIVATE)

        val iduserconnect=sharedPreferences.getString("USER_ID", "")

        binding = FragmentPostBinding.inflate(layoutInflater)
        val postservice= ClientObject.buildService(PostService::class.java)
        val fragmentmanager=requireFragmentManager();
        postservice.getPost().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    val ListPost = response.body()!!
                    binding.rvPost.adapter = PostAdapter(ListPost,fragmentmanager,iduserconnect!!)

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
            startActivity(Intent(requireContext(),AddPost::class.java))
        }
        binding.rvPost.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        return binding.root

    }


}