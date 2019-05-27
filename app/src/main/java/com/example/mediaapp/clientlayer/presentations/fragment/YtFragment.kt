package com.example.mediaapp.clientlayer.presentations.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders

import com.example.mediaapp.clientlayer.presentations.MainViewModel
import com.example.mediaapp.databinding.FragmentYtBinding
import com.example.mediaapp.framework.BaseApp
import com.example.mediaapp.viewmodellayer.ViewModelFactory
import javax.inject.Inject



class YtFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MainViewModel
    private lateinit var fragmentYtBinding: FragmentYtBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseApp.viewModelComponent.doInjection(this)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(MainViewModel::class.java)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        fragmentYtBinding = FragmentYtBinding.inflate(inflater,container,false)
        return fragmentYtBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(activity,"hello",Toast.LENGTH_LONG).show()
        Log.d("data","hello")
    }


}
