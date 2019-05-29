package com.example.mediaapp.clientlayer.presentations

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mediaapp.clientlayer.presentations.fragment.YtFragment
import com.example.mediaapp.domainlayer.model.Media
import java.util.*

class BindingAdapter {
    companion object {
        @JvmStatic
        @BindingAdapter("postList")
        fun setPostList(view: RecyclerView,mediaList:List<Media>?){
            if (mediaList!=null) {
                (Objects.requireNonNull(view.adapter) as YtFragment.YtAdapter).setMovieList(mediaList)
            }
        }
    }
}