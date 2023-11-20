package tn.esprit.ecoshope.ui.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.databinding.SingleItemPostBinding
import tn.esprit.ecoshope.model.Comment
import tn.esprit.ecoshope.model.Post
import tn.esprit.ecoshope.model.UserConnect
import tn.esprit.ecoshope.ui.fragment.PostDetailFragment
import tn.esprit.ecoshope.util.post.ApiPost

class PostAdapter (val postlist:List<Post>,private val fragmentManager: FragmentManager) :RecyclerView.Adapter<PostAdapter.PostHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
       val binding=SingleItemPostBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)
    }
    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        with(holder){
            with(postlist[position]){
                val onepost=postlist[position]
                val postapi=ApiPost.create()
               postapi.detailUser(iduser).enqueue(object :Callback<UserConnect>{
                   override fun onResponse(
                       call: Call<UserConnect>,
                       response: Response<UserConnect>
                   ) {if(response.isSuccessful){
                        val userpost=response.body()!!

                        binding.blogUserName.text=userpost.Username

                   }
                   }

                   override fun onFailure(call: Call<UserConnect>, t: Throwable) {
                       Log.e("Network Error", t.toString())

                   }

               })


                postapi.getAllCommentPost(id).enqueue(object :Callback<List<Comment>>{
                    override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                        if(response.isSuccessful){
                            Log.d("commentaret", "onResponse:${response.body()} ")
                            val listcomment=response.body()!!
                            binding.blogCommentCount.text=listcomment.size.toString()



                        }
                    }

                    override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                    }

                })


               binding.idpost.text=id
                //binding.blogUserName.text=user[0].Username
                binding.blogDescription.text=content
                binding.blogDate.text=publicationDate
                binding.blogLikeCount.text=likes.size.toString()
                Picasso.get().load(media).into(binding.blogImage)
                binding.root.setOnClickListener{
                    navigateToPostDetails(fragmentManager,onepost)
                }
            }
        }
    }
    override fun getItemCount(): Int {
        return postlist.size
    }



    inner class PostHolder(val binding: SingleItemPostBinding):RecyclerView.ViewHolder(binding.root){

    }

    private fun navigateToPostDetails(fragmentManager: FragmentManager,post:Post) {
        val bundle =Bundle().apply {
            putParcelable("p",post)

        }
        val postdetailsfragment=PostDetailFragment()
        postdetailsfragment.arguments=bundle
        fragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, postdetailsfragment)
            addToBackStack(null)
            commit()
        }
    }


}