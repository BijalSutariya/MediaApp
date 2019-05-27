package com.example.mediaapp.clientlayer.presentations.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

import com.example.mediaapp.R
import com.example.mediaapp.clientlayer.presentations.MainViewModel
import com.example.mediaapp.framework.BaseApp
import com.example.mediaapp.viewmodellayer.ViewModelFactory
import javax.inject.Inject

class YtFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseApp.viewModelComponent.doInjection(this)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(MainViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_yt, container, false)
    }


}
