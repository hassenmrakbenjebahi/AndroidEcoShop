package tn.esprit.ecoshope.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.ecoshope.databinding.SingleItemPostBinding
import tn.esprit.ecoshope.model.Post

class PostAdapter (val postlist:MutableList<Post>) :RecyclerView.Adapter<PostAdapter.PostHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
       val binding=SingleItemPostBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)
    }
    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        with(holder){
            with(postlist[position]){
                binding.blogUserName.text=author
                binding.blogDescription.text=content
            }
        }
    }
    override fun getItemCount(): Int {
        return postlist.size
    }



    inner class PostHolder(val binding: SingleItemPostBinding):RecyclerView.ViewHolder(binding.root)

}