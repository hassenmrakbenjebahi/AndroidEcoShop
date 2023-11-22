package tn.esprit.ecoshope.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.databinding.SingleItemCommentBinding
import tn.esprit.ecoshope.model.Post.Comment
import tn.esprit.ecoshope.model.Post.UserConnect
import tn.esprit.ecoshope.util.ClientObject
import tn.esprit.ecoshope.util.post.PostService

class CommentAdapter(val commentlist:List<Comment>): RecyclerView.Adapter<CommentAdapter.CommentHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        val binding=
            SingleItemCommentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CommentHolder(binding)
    }

    override fun getItemCount(): Int {
        return commentlist.size
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        with(holder){
            with(commentlist[position]){
                val postservice= ClientObject.buildService(PostService::class.java)

                postservice.detailUser(iduser).enqueue(object : Callback<UserConnect> {
                    override fun onResponse(
                        call: Call<UserConnect>,
                        response: Response<UserConnect>
                    ) {if(response.isSuccessful){
                        val usercomment=response.body()!!

                        binding.cCommentUsername.text=usercomment.Username
                        Glide.with(binding.root)
                            .load(usercomment.Image)
                            .apply(
                                RequestOptions()
                                    .placeholder(R.drawable.starbucks_background) // Optional placeholder image
                                    .error(R.drawable.jk_placeholder_image) // Optional error image
                                    .diskCacheStrategy(DiskCacheStrategy.ALL) // Optional: Caching strategy
                            )
                            .into(binding.cUserImage)
                    }
                    }

                    override fun onFailure(call: Call<UserConnect>, t: Throwable) {
                        Log.e("Network Error", t.toString())

                    }

                })
                binding.cCommentMessage.text=content

            }
        }
    }



    inner class CommentHolder(val binding: SingleItemCommentBinding): RecyclerView.ViewHolder(binding.root){
    }
}