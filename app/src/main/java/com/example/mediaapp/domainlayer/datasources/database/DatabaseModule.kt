package com.example.mediaapp.domainlayer.datasources.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mediaapp.domainlayer.model.Media
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule constructor(private var context: Context) {

    @Provides
    @Singleton
    fun getMediaDataBase(): MediaDatabase {
        return Room.databaseBuilder(
            context,
            MediaDatabase::class.java, "media_database"
        ).build()
    }

    @Dao
    interface MediaDao {
        @get:Query("SELECT * from media")
        val mediaList: LiveData<List<Media>>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insert(mediaList: List<Media>): LongArray

        @Query("SELECT * from media WHERE id = :id")
        fun getMovie(id: String): LiveData<Media>

        @Query("DELETE FROM media")
        fun removeAllMedia()
    }

    @Database(entities = [Media::class], version = 1, exportSchema = false)
    abstract class MediaDatabase : RoomDatabase() {
        abstract fun mediaDao(): MediaDao
    }
}