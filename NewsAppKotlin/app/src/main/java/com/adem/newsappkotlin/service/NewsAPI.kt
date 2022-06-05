package com.adem.newsappkotlin.service

import com.adem.newsappkotlin.model.Article
import com.adem.newsappkotlin.model.NewsResponse
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET

interface NewsAPI {
    //https://newsapi.org/v2/top-headlines?country=us&apiKey=5ef8fdd9f3f94529a4f6f2fdd858e6e6

    @GET("top-headlines?country=us&apiKey=5ef8fdd9f3f94529a4f6f2fdd858e6e6")
    fun getNews() : Single<NewsResponse>

}