package tn.esprit.ecoshope.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.databinding.FragmentPostDetailBinding
import tn.esprit.ecoshope.model.Comment
import tn.esprit.ecoshope.model.Post
import tn.esprit.ecoshope.model.UserConnect
import tn.esprit.ecoshope.ui.adapter.CommentAdapter
import tn.esprit.ecoshope.ui.adapter.PostAdapter
import tn.esprit.ecoshope.util.post.ApiPost

class PostDetailFragment:Fragment() {
    private lateinit var binding: FragmentPostDetailBinding
    private lateinit var btncomment:ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentPostDetailBinding.inflate(layoutInflater)
        // Récupérez le Bundle de la méthode getArguments()
        val    post = arguments?.getParcelable<Post>("p")!!

       // val username: String? = post.user.firstOrNull()?.Username
         btncomment=binding.commentPostBtn
        val iduserconnect="6553fe22539c1e3985881aa2"
        val idpost=post.id
        btncomment.setOnClickListener {
            if(!validate()){
                Snackbar.make(binding.root,"Comment is empty",Snackbar.LENGTH_SHORT).show()

            }else {


                val postapi = ApiPost.create()
                postapi.addComment(
                    idpost,
                    iduserconnect,
                    ApiPost.newcommentpost(binding.commentField.text.toString())
                ).enqueue(
                    object : Callback<Comment> {
                        override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                            if (response.isSuccessful) {
                                Snackbar.make(
                                    binding.root,
                                    "Comment added successfully",
                                    Snackbar.LENGTH_SHORT
                                ).show()

                            } else {
                                   Snackbar.make(
                                    binding.root,
                                    "Error adding comment",
                                    Snackbar.LENGTH_SHORT
                                ).show()


                            }
                        }

                        override fun onFailure(call: Call<Comment>, t: Throwable) {
                        }
                    }
                )
            }
        }


        val posrapi=ApiPost.create()
        posrapi.getAllCommentPost(idpost).enqueue(object :Callback<List<Comment>>{
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if(response.isSuccessful){
                    Log.d("commentaret", "onResponse:${response.body()} ")
                    val listcomment=response.body()!!
                    binding.rvComment.adapter = CommentAdapter(listcomment)
                    binding.dBlogCommentCount.text=listcomment.size.toString()



                }
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
            }

        })


        posrapi.detailUser(post.iduser).enqueue(object :Callback<UserConnect>{
            override fun onResponse(
                call: Call<UserConnect>,
                response: Response<UserConnect>
            ) {if(response.isSuccessful){
                val userpost=response.body()!!

                binding.dBlogUserName.text=userpost.Username


            }
            }

            override fun onFailure(call: Call<UserConnect>, t: Throwable) {
                Log.e("Network Error", t.toString())

            }

        })




                binding.dBlogDate.text=post.publicationDate
                binding.dBlogDescription.text=post.content
                binding.dBlogLikeCount.text=post.likes.size.toString()

               binding.rvComment.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        return binding.root

    }

     private fun validate():Boolean{
         if(binding.commentField.text!!.isEmpty()){
             return false
         }
         return true

     }

}