package tn.esprit.ecoshope.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.ecoshope.databinding.SingleItemCommentBinding
import tn.esprit.ecoshope.databinding.SingleItemPostBinding
import tn.esprit.ecoshope.model.Comment
import tn.esprit.ecoshope.model.Post

class CommentAdapter(val commentlist:List<Comment>):RecyclerView.Adapter<CommentAdapter.CommentHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        val binding=SingleItemCommentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CommentHolder(binding)
    }

    override fun getItemCount(): Int {
        return commentlist.size
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        with(holder){
            with(commentlist[position]){
               binding.cCommentUsername.text=author
                binding.cCommentMessage.text=content

            }
        }
    }
    inner class CommentHolder(val binding: SingleItemCommentBinding): RecyclerView.ViewHolder(binding.root){
    }
}