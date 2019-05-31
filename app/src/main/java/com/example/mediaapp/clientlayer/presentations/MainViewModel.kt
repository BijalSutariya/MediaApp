package com.example.mediaapp.clientlayer.presentations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.mediaapp.domainlayer.DataRequest
import com.example.mediaapp.domainlayer.model.Media
import com.example.mediaapp.domainlayer.repositories.MediaRepository

import javax.inject.Inject
class MainViewModel @Inject constructor(private var repository: MediaRepository):ViewModel() {

    private var mediaIdLiveData: MutableLiveData<Int> = MutableLiveData()
    private var mediaListData:MutableLiveData<List<Media>> = MutableLiveData()
    private var mediaListTrigger:MutableLiveData<Boolean> = MutableLiveData()

    fun getResponseList() :LiveData<DataRequest<List<Media>>>{
        return Transformations.switchMap(mediaListTrigger) { repository.getMediaList() }
    }

    fun getMediaList(): MutableLiveData<List<Media>> {
        return mediaListData
    }

    fun getMediaListTrigger(): MutableLiveData<Boolean> {
        return mediaListTrigger
    }

    fun getMediaIdInt(): MutableLiveData<Int> {
        return mediaIdLiveData
    }

    fun getMediaDetails():LiveData<DataRequest<Media>>{
        return Transformations.switchMap(mediaIdLiveData){newMediaId->repository.getMediaDetails(newMediaId)}
    }


}