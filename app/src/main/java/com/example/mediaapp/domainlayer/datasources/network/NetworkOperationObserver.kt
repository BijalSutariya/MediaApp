package com.example.mediaapp.domainlayer.datasources.network

import androidx.lifecycle.MutableLiveData
import com.example.mediaapp.domainlayer.DataRequest
import com.example.mediaapp.domainlayer.managers.DataManager
import com.example.mediaapp.domainlayer.model.Media
import com.example.mediaapp.domainlayer.model.Response
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class NetworkOperationObserver constructor(
    private var responseFromNetwork: MutableLiveData<DataRequest<List<Media>>>,
    private var dataManager: DataManager<*, *>
) : Observer<List<Media>> {
    override fun onNext(t: List<Media>) {
        responseFromNetwork.value = DataRequest.success(t)
    }

    override fun onComplete() {
    }

    override fun onSubscribe(d: Disposable) {
    }


    override fun onError(e: Throwable) {
        this.responseFromNetwork.setValue(DataRequest.error(e.localizedMessage.toString(), null))
        dataManager.onFetchFailed(e)
    }
}
