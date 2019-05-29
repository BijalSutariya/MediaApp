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


    private var mediaListData:MutableLiveData<List<Media>> = MutableLiveData()
    private var mediaListTrigger:MutableLiveData<Boolean> = MutableLiveData()

    fun getResponseList() :LiveData<DataRequest<List<Media>>>{
        return Transformations.switchMap(mediaListTrigger) { reponse -> repository.getMediaList() }
    }

    fun getMediaList(): MutableLiveData<List<Media>> {
        return mediaListData
    }

    fun getMediaListTrigger(): MutableLiveData<Boolean> {
        return mediaListTrigger
    }


}