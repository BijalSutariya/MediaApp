package com.example.mediaapp.framework

import android.app.Application
import com.example.mediaapp.domainlayer.datasources.database.DatabaseModule
import com.example.mediaapp.domainlayer.datasources.network.NetworkModule
import com.example.mediaapp.framework.dicomponents.DaggerDataLayerComponent
import com.example.mediaapp.framework.dicomponents.DaggerViewModelComponent
import com.example.mediaapp.framework.dicomponents.DataLayerComponent
import com.example.mediaapp.framework.dicomponents.ViewModelComponent
import com.example.mediaapp.viewmodellayer.ViewModelModule

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        setDataLayerComponent()
        setViewModelComponent()
    }

    private fun setDataLayerComponent() {
        dataLayerComponent = DaggerDataLayerComponent.builder()
            .networkModule(NetworkModule(this))
            .databaseModule(DatabaseModule(this))
            .build()
    }

    private fun setViewModelComponent() {
        viewModelComponent = DaggerViewModelComponent.builder()
            .viewModelModule(ViewModelModule())
            .build()
    }

    companion object {

        lateinit var dataLayerComponent: DataLayerComponent

        lateinit var viewModelComponent: ViewModelComponent
    }
}