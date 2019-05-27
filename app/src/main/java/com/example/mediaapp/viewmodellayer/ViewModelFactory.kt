package com.example.mediaapp.viewmodellayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mediaapp.clientlayer.presentations.MainViewModel
import com.example.mediaapp.domainlayer.repositories.MediaRepository
import io.reactivex.annotations.NonNull
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ViewModelFactory
@Inject
constructor(private val repository: MediaRepository) : ViewModelProvider.Factory {


    @NonNull
    override fun <T : ViewModel> create(
        @NonNull modelClass: Class<T>
    ): T {
        /*
         * Here we can create various view Models.*/
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}