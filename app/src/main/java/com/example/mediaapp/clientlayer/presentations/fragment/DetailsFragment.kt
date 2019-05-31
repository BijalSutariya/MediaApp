package com.example.mediaapp.clientlayer.presentations.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders

import com.example.mediaapp.clientlayer.presentations.MainViewModel
import com.example.mediaapp.clientlayer.presentations.MediaPresenter
import com.example.mediaapp.clientlayer.presentations.ViewController
import com.example.mediaapp.databinding.FragmentDetailsBinding
import com.example.mediaapp.framework.BaseApp
import com.example.mediaapp.viewmodellayer.ViewModelFactory
import javax.inject.Inject


class DetailsFragment : Fragment(),ViewController{

    override fun getLifeCycleOwner(): LifecycleOwner {
        return viewLifecycleOwner
    }

    override fun onErrorOccurred(message: String) {
    }

    override fun onSucceed() {
    }

    override fun onLoadingOccurred() {
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MainViewModel
    private lateinit var presenter:MediaPresenter

    private lateinit var detailsBinding: FragmentDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseApp.viewModelComponent.doInjection(this)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailsBinding = FragmentDetailsBinding.inflate(inflater,container,false)
        return detailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = MediaPresenter(viewModel, this, MediaPresenter.PresenterType.DETAILS)
        detailsBinding.mediaPresenter = presenter
        detailsBinding.lifecycleOwner = getLifeCycleOwner()
    }

    override fun onStart() {
        super.onStart()
        val bundle = arguments
        if (bundle!=null){
            presenter.getMediaIdLiveData().value=bundle.getInt("mediaId")
        }
    }
}
