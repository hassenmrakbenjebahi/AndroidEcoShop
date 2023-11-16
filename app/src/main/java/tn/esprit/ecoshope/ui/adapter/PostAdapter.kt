package tn.esprit.ecoshope.ui.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.databinding.SingleItemPostBinding
import tn.esprit.ecoshope.model.Post
import tn.esprit.ecoshope.ui.fragment.PostDetailFragment

class PostAdapter (val postlist:List<Post>,private val fragmentManager: FragmentManager) :RecyclerView.Adapter<PostAdapter.PostHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
       val binding=SingleItemPostBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)
    }
    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        with(holder){
            with(postlist[position]){
                val onepost=postlist[position]

                binding.blogUserName.text=user[position].Username
                binding.blogDescription.text=content
                binding.blogDate.text=publicationDate
                binding.blogLikeCount.text=likes.size.toString()
                binding.blogCommentCount.text=comment.size.toString()
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