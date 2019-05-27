package com.example.mediaapp.clientlayer.presentations

import androidx.lifecycle.ViewModel
import com.example.mediaapp.domainlayer.repositories.MediaRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(private var repository: MediaRepository):ViewModel() {

}