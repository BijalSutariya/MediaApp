package com.example.mediaapp.domainlayer.datasources.network

import com.example.mediaapp.framework.BaseApp
import com.example.mediaapp.framework.helpers.NetworkUtil
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Module
class NetworkModule constructor(var baseApp: BaseApp) {

    @Provides
    @Singleton
    fun getHttpClient(): OkHttpClient {
        val interceptor = Interceptor {chain->
            val originalResponse = chain.proceed(chain.request())
            if (NetworkUtil.isNetworkConnected(baseApp)){
                val maxAge = 60 // read from cache for 1 minute
                return@Interceptor originalResponse.newBuilder().header("Cache-Control", "public, max-age=$maxAge").build()
            }else {
                val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
                return@Interceptor originalResponse.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=$maxStale").build()
            }
        }

        val httpCacheDirectory = File(baseApp.getCacheDir(), "responses")
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        val cache = Cache(httpCacheDirectory, cacheSize.toLong())
        return OkHttpClient.Builder().addInterceptor(interceptor).cache(cache).build()
    }

    @Provides
    @Singleton
    @Inject
    fun getRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiUrls.baseUrl).client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiUrls.baseUrl)
            .build()
    }

    @Provides
    @Singleton
    @Inject
    fun getMovieListApi(retrofit: Retrofit): MediaApiList {
        return retrofit.create<MediaApiList>(MediaApiList::class.java)
    }

    interface MediaApiList{

    }

}