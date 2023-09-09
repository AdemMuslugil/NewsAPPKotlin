package com.adem.newsappkotlin.service

import com.adem.newsappkotlin.Util.API_KEY
import com.adem.newsappkotlin.model.Article
import com.adem.newsappkotlin.model.NewsResponse
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("top-headlines?country=us")
    fun getNews(@Query("apiKey") apiKey: String = API_KEY) : Single<NewsResponse>

}