package com.adem.newsappkotlin.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adem.newsappkotlin.db.NewsDAO
import com.adem.newsappkotlin.db.NewsDatabase
import com.adem.newsappkotlin.model.Article
import kotlinx.coroutines.launch

class DetailsViewModel(application: Application) : BaseViewModel(application) {

    val newsLiveData = MutableLiveData<Article>()

    fun getDataFromRoom(id : Int){

        launch {

            val dao = NewsDatabase(getApplication()).newsDao()
            val news = dao.getNews(id)
            newsLiveData.value = news

        }

    }


}