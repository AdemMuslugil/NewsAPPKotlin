package com.adem.newsappkotlin.service


import com.adem.newsappkotlin.model.Article
import com.adem.newsappkotlin.model.NewsResponse
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.internal.operators.observable.ObservableLastMaybe
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NewsAPIService {

    private val BASE_URL = "https://newsapi.org/v2/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(NewsAPI::class.java)


    fun getData() : Single<NewsResponse>{
        return api.getNews()
    }

}