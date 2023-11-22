package tn.esprit.ecoshope.ui.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.ecoshope.R
import tn.esprit.ecoshope.databinding.SingleItemPostBinding
import tn.esprit.ecoshope.model.Post.Post
import tn.esprit.ecoshope.model.Post.UserConnect
import tn.esprit.ecoshope.ui.fragment.PostDetailFragment
import tn.esprit.ecoshope.util.ClientObject
import tn.esprit.ecoshope.util.post.PostService
import com.bumptech.glide.request.RequestOptions
import tn.esprit.ecoshope.model.Post.Comment


class PostAdapter (val postlist:List<Post>, private val fragmentManager: FragmentManager,val iduserconnect:String) :
    RecyclerView.Adapter<PostAdapter.PostHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding= SingleItemPostBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)
    }
    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        with(holder){
            with(postlist[position]){
                val onepost=postlist[position]

                val postservice= ClientObject.buildService(PostService::class.java)

                postservice.detailUser(iduser).enqueue(object : Callback<UserConnect> {
                    override fun onResponse(
                        call: Call<UserConnect>,
                        response: Response<UserConnect>
                    ) {if(response.isSuccessful){
                        val userpost=response.body()!!

                        binding.blogUserName.text=userpost.Username
                        Glide.with(binding.root)
                            .load(userpost.Image)
                            .apply(
                                RequestOptions()
                                    .placeholder(R.drawable.starbucks_background) // Optional placeholder image
                                    .error(R.drawable.jk_placeholder_image) // Optional error image
                                    .diskCacheStrategy(DiskCacheStrategy.ALL) // Optional: Caching strategy
                            )
                            .into(binding.blogUserImage)

                    }
                    }

                    override fun onFailure(call: Call<UserConnect>, t: Throwable) {
                        Log.e("Network Error", t.toString())

                    }

                })


                postservice.getAllCommentPost(id).enqueue(object : Callback<List<Comment>> {
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

                if(searchlike(likes,iduser)){
                    binding.blogLike2Btn.visibility= View.VISIBLE
                }else{

                    binding.blogLikeBtn.visibility=View.VISIBLE

                }


                binding.blogLikeBtn.setOnClickListener{
                    val servicepost=ClientObject.buildService(PostService::class.java)
                    servicepost.addlike(id,iduserconnect).enqueue(object: Callback<Post> {
                        override fun onResponse(call: Call<Post>, response: Response<Post>) {
                            if(response.isSuccessful){
                                binding.blogLike2Btn.visibility=View.VISIBLE
                                binding.blogLikeBtn.visibility=View.INVISIBLE
                                //notifyDataSetChanged()


                            }
                        }

                        override fun onFailure(call: Call<Post>, t: Throwable) {
                            Log.e("erroradd", "onFailure: error", )
                        }

                    })
                }

                binding.blogLike2Btn.setOnClickListener{
                    val servicepost=ClientObject.buildService(PostService::class.java)
                    servicepost.retirelike(id,iduserconnect).enqueue(object: Callback<Post> {
                        override fun onResponse(call: Call<Post>, response: Response<Post>) {
                            if(response.isSuccessful){
                                binding.blogLike2Btn.visibility=View.INVISIBLE
                                binding.blogLikeBtn.visibility=View.VISIBLE

                                //notifyDataSetChanged()
                            }
                        }

                        override fun onFailure(call: Call<Post>, t: Throwable) {
                            Log.e("errorretire", "onFailure: error", )
                        }

                    })
                }


                binding.idpost.text=id
                binding.blogDescription.text=content
                binding.blogDate.text=publicationDate
                binding.blogLikeCount.text=likes.size.toString()
                // Use Glide to load the image into the ImageView
                Glide.with(binding.root)
                    .load(media)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.starbucks_background) // Optional placeholder image
                            .error(R.drawable.jk_placeholder_image) // Optional error image
                            .diskCacheStrategy(DiskCacheStrategy.ALL) // Optional: Caching strategy
                    )
                    .into(binding.blogImage)
                binding.root.setOnClickListener{
                    navigateToPostDetails(fragmentManager,onepost)
                }

            }
        }
    }
    override fun getItemCount(): Int {
        return postlist.size
    }



    inner class PostHolder(val binding: SingleItemPostBinding): RecyclerView.ViewHolder(binding.root){

    }

    private fun navigateToPostDetails(fragmentManager: FragmentManager, post:Post) {
        val bundle = Bundle().apply {
            putParcelable("p",post)

        }
        val postdetailsfragment= PostDetailFragment()
        postdetailsfragment.arguments=bundle
        fragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, postdetailsfragment)
            addToBackStack(null)
            commit()
        }
    }

    private fun searchlike( list:List<String>, iduser:String):Boolean{
        var test=false
        for(i in 0 until list.size)
            if(list[i]==iduser){
                test=true
            }
        return test

    }




}