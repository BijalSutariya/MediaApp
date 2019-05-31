package com.example.mediaapp.domainlayer.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mediaapp.domainlayer.DataRequest
import com.example.mediaapp.domainlayer.datasources.database.DatabaseModule
import com.example.mediaapp.domainlayer.datasources.network.NetworkModule
import com.example.mediaapp.domainlayer.datasources.network.NetworkOperationObserver
import com.example.mediaapp.domainlayer.managers.DataManager
import com.example.mediaapp.domainlayer.managers.FetchLimiter
import com.example.mediaapp.domainlayer.model.Media
import com.example.mediaapp.framework.BaseApp
import com.example.mediaapp.framework.TaskExecutors
import io.reactivex.schedulers.Schedulers
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

    fun getMediaList(): LiveData<DataRequest<List<Media>>> {

        return object : DataManager<DataRequest<List<Media>>, List<Media>>(taskExecutor) {
            override fun loadFromDatabase(): LiveData<List<Media>> {
                return mediaDatabase.mediaDao().mediaList
            }

            override fun loadFromNetwork(): LiveData<DataRequest<List<Media>>>? {
                val responseFromNetwork = MutableLiveData<DataRequest<List<Media>>>()
                mediaApiList.mediaList()
                    .subscribeOn(Schedulers.from(taskExecutors.networkOperationThread))
                    .observeOn(Schedulers.from(taskExecutor.mainThread))
                    .subscribe(NetworkOperationObserver(responseFromNetwork, this))
                return responseFromNetwork
            }

            override fun shouldFetchData(data: List<Media>): Boolean {
                return data.isEmpty()
            }

            override fun saveDataToDatabase(data: List<Media>) {
                mediaDatabase.mediaDao().insert(data)
            }

            override fun clearPreviousData() {
            }

            override fun processResponse(response: DataRequest<List<Media>>): List<Media>? {
                return if (response.data == null)
                    null
                else
                    response.data
            }

        }.toLiveData()

    }

    fun getMediaDetails(newMediaId: Int): LiveData<DataRequest<Media>>? {
        return object : DataManager<Media, Media>(taskExecutor) {
            override fun loadFromDatabase(): LiveData<Media> {
                return mediaDatabase.mediaDao().getMovie(newMediaId)
            }

            override fun loadFromNetwork(): LiveData<Media>? {
                return null
            }

            override fun shouldFetchData(data: Media): Boolean {
                return false
            }

            override fun saveDataToDatabase(data: Media) {
            }

            override fun clearPreviousData() {
            }

            override fun processResponse(response: Media): Media? {
                return null
            }

        }.toLiveData()
    }


}
