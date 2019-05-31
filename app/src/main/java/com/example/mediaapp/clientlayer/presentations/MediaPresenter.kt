package com.example.mediaapp.clientlayer.presentations

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.mediaapp.domainlayer.DataRequest
import com.example.mediaapp.domainlayer.model.Media

class MediaPresenter(
    mainViewModel: MainViewModel,
    private var viewController: ViewController,
    private var presenterType: PresenterType
) {
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    private lateinit var mediaIdLiveData: MutableLiveData<Int>
    //response
    private lateinit var mediaList: MutableLiveData<List<Media>>
    private lateinit var isTrigger: MutableLiveData<Boolean>
    private lateinit var media: MutableLiveData<Media>

    init {
        if (this.presenterType == PresenterType.LIST) {
            mediaList = mainViewModel.getMediaList()
            isTrigger = mainViewModel.getMediaListTrigger()
            mainViewModel.getResponseList()
                .observe(viewController.getLifeCycleOwner(),
                    Observer<DataRequest<List<Media>>> {
                        this.consumeResponse(it)
                    })
        } else {
            media = MutableLiveData()
            mediaIdLiveData = mainViewModel.getMediaIdInt()
            mainViewModel.getMediaDetails().observe(viewController.getLifeCycleOwner(),
                Observer<DataRequest<Media>> {
                    this.consumeResponse(it)
                })
        }

    }

    private fun consumeResponse(dataRequest: DataRequest<*>) {
        when (dataRequest.currentState) {
            DataRequest.Status.LOADING -> {
                isLoading.value = true
                if (presenterType == PresenterType.LIST) {
                    mediaList.value = dataRequest.data as List<Media>?
                }else{
                    media.value = dataRequest.data as Media?
                }
                Log.d("media status", "" + dataRequest.currentState)
                viewController.onLoadingOccurred()

            }
            DataRequest.Status.SUCCESS -> {
                isLoading.value = false

                if (presenterType == PresenterType.LIST) {
                    mediaList.value = dataRequest.data as List<Media>?
                    Log.d("media List", "" + mediaList)
                }
                else{
                    media.value= dataRequest.data as Media?
                    Log.d("media Data",media.toString())
                }
                Log.d("media status", "" + dataRequest.currentState)

                viewController.onSucceed()
            }
            DataRequest.Status.ERROR -> {
                isLoading.value = false
                viewController.onErrorOccurred("Error")
                Log.d("media List", "" + dataRequest.message)
            }
        }
    }

    fun getTrigger(): MutableLiveData<Boolean> {
        return isTrigger
    }

    fun getMediaIdLiveData(): MutableLiveData<Int> {
        return mediaIdLiveData
    }

    fun getMediaList(): MutableLiveData<List<Media>> {
        return mediaList
    }

    fun getMedia(): MutableLiveData<Media> {
        return media
    }

    enum class PresenterType {
        LIST,
        DETAILS
    }
}