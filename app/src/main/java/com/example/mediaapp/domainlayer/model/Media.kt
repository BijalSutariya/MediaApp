package com.example.mediaapp.domainlayer.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Media(@field:PrimaryKey
            var id: String,
            var name: String)