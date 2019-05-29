package com.example.mediaapp.domainlayer.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class Media(
    @SerializedName("userId")
    var userId : Int,

    @field:PrimaryKey
    var id: Int,

    @SerializedName("title")
    var title: String,

    @SerializedName("body")
    var body:String)