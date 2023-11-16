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
import tn.esprit.ecoshope.ui.adapter.CommentAdapter
import tn.esprit.ecoshope.ui.adapter.PostAdapter
import tn.esprit.ecoshope.util.post.ApiPost

class PostDetailFragment:Fragment() {
    private lateinit var binding: FragmentPostDetailBinding
    private lateinit var btncomment:ImageView
    private lateinit var commenttext:EditText
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentPostDetailBinding.inflate(layoutInflater)
        // Récupérez le Bundle de la méthode getArguments()
        val    post = arguments?.getParcelable<Post>("p")!!

        val username: String? = post.user.firstOrNull()?.Username
         btncomment=binding.commentPostBtn
        val iduserconnect="6553fdd1539c1e3985881aa1"
        val idpost="6555656c94db69b2313d4676"
        btncomment.setOnClickListener {
            val postapi= ApiPost.create()
            postapi.addComment(idpost,iduserconnect,ApiPost.newcommentpost(binding.commentField.text.toString())).enqueue(
                object :Callback<Comment>{
                    override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                        if(response.isSuccessful){
                            Log.d("comment", "onResponse: comment added ")
                        }else{
                            Log.e("nocomment", "onResponse: ", )
                        }
                    }

                    override fun onFailure(call: Call<Comment>, t: Throwable) {
                    }
                }
            )

        }

        binding.dBlogUserName.text=username
                binding.dBlogDate.text=post.publicationDate
                binding.dBlogDescription.text=post.content
                binding.dBlogCommentCount.text=post.comment.size.toString()
                binding.dBlogLikeCount.text=post.likes.size.toString()

                binding.rvComment.adapter = CommentAdapter(post.comment)
               binding.rvComment.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        return binding.root

    }



}