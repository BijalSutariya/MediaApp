package com.example.mediaapp.domainlayer.repositories

import com.example.mediaapp.domainlayer.datasources.database.DatabaseModule
import com.example.mediaapp.domainlayer.datasources.network.NetworkModule
import com.example.mediaapp.domainlayer.managers.FetchLimiter
import com.example.mediaapp.framework.BaseApp
import com.example.mediaapp.framework.TaskExecutors
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaRepository
@Inject
constructor() {

    @Inject
    lateinit var taskExecutor: TaskExecutors
    @Inject
    lateinit var mediaApiList: NetworkModule.MediaApiList
    @Inject
    lateinit var mediaDatabase: DatabaseModule.MediaDatabase

    private val mediaFetchLimiter: FetchLimiter

    init {
        BaseApp.dataLayerComponent.doInjection(this)
        mediaFetchLimiter = FetchLimiter(10, TimeUnit.MINUTES)
    }
}