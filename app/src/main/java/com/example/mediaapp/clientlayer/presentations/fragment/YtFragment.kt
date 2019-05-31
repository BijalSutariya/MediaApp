package com.example.mediaapp.clientlayer.presentations.fragment

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mediaapp.R
import com.example.mediaapp.clientlayer.presentations.MainViewModel
import com.example.mediaapp.clientlayer.presentations.MediaPresenter
import com.example.mediaapp.clientlayer.presentations.ViewController
import com.example.mediaapp.databinding.FragmentYtBinding
import com.example.mediaapp.databinding.YtItemListBinding
import com.example.mediaapp.domainlayer.model.Media
import com.example.mediaapp.framework.BaseApp
import com.example.mediaapp.viewmodellayer.ViewModelFactory
import java.util.*
import javax.inject.Inject

class YtFragment : Fragment(), ViewController {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MainViewModel
    private lateinit var fragmentYtBinding: FragmentYtBinding
    private var savedInstanceState: Bundle? = null
    private val RECYCLER_VIEW_STATE_KEY = "RECYCLER_VIEW_STATE_KEY"

    override fun getLifeCycleOwner(): LifecycleOwner {
        return viewLifecycleOwner
    }

    override fun onErrorOccurred(message: String) {
    }

    override fun onSucceed() {
        savedInstanceState = arguments
        if (savedInstanceState != null) {
            val savedRecyclerLayoutState = savedInstanceState!!.getParcelable<Parcelable>(RECYCLER_VIEW_STATE_KEY)
            Objects.requireNonNull(fragmentYtBinding.rvDataList.layoutManager)?.onRestoreInstanceState(savedRecyclerLayoutState)
        }
    }

    override fun onLoadingOccurred() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseApp.viewModelComponent.doInjection(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        fragmentYtBinding = FragmentYtBinding.inflate(inflater, container, false)
        return fragmentYtBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.savedInstanceState = savedInstanceState
        setUpInitialThings(view)

        val presenter = MediaPresenter(viewModel, this, MediaPresenter.PresenterType.LIST)
        fragmentYtBinding.mediaPresenter = presenter
        fragmentYtBinding.lifecycleOwner = getLifeCycleOwner()
        presenter.getTrigger().value=true
    }

    private fun setUpInitialThings(view: View) {
        val ytAdapter= object : YtAdapter(){
            override fun onMediaItemClicked(id: Int) {
                val bundle = Bundle()
                bundle.putInt("mediaId",id)
                Navigation.findNavController(view).navigate(R.id.testFragment,bundle)
            }
        }
        fragmentYtBinding.rvDataList.layoutManager = LinearLayoutManager(activity)
        fragmentYtBinding.rvDataList.adapter = ytAdapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(
            RECYCLER_VIEW_STATE_KEY,
            Objects.requireNonNull(fragmentYtBinding.rvDataList.layoutManager)!!.onSaveInstanceState()
        )
    }
    abstract inner class YtAdapter internal constructor() :
        RecyclerView.Adapter<YtAdapter.ViewHolder>() {

        private var mediaList: List<Media>

        init {
            this.mediaList = ArrayList()
        }

        internal fun setMovieList(mediaList: List<Media>) {
            this.mediaList = mediaList
            notifyDataSetChanged()
        }
        internal abstract fun onMediaItemClicked(id: Int)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = YtItemListBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return mediaList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val media = mediaList[position]
            holder.ytItemListBinding.root.setOnClickListener { onMediaItemClicked(media.id) }
            holder.bind(media)

        }

        inner class ViewHolder(val ytItemListBinding: YtItemListBinding) :
            RecyclerView.ViewHolder(ytItemListBinding.root) {

            fun bind(media: Media) {
                ytItemListBinding.media = media
                ytItemListBinding.executePendingBindings()
            }
        }
    }
}
