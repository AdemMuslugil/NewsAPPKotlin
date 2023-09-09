package com.adem.newsappkotlin.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.adem.newsappkotlin.Util.CustomPreferences
import com.adem.newsappkotlin.db.NewsDatabase
import com.adem.newsappkotlin.model.Article
import com.adem.newsappkotlin.model.NewsResponse

import com.adem.newsappkotlin.service.NewsAPIService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.internal.operators.flowable.FlowableBlockingSubscribe.subscribe
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonArray
import okio.Utf8.size
import java.nio.file.Files.newBufferedWriter
import java.nio.file.Files.size

class HomeViewModel(application: Application) : BaseViewModel(application) {

    private val newsAPIService = NewsAPIService()
    private val disposable = CompositeDisposable()
    private var customPreferences = CustomPreferences(getApplication())
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L

    val newsData = MutableLiveData<NewsResponse>()
    val newsDataFromSql = MutableLiveData<List<Article>>()
    val newsError = MutableLiveData<Boolean>()
    val newsLoading=MutableLiveData<Boolean>()


    fun refreshFromAPI(){
        getDataFromAPI()
    }


    fun refreshData(){

        newsLoading.value = true
        newsError.value = true
        val updateTime = customPreferences.getTime()
        if(updateTime != null && updateTime!= 0L && System.nanoTime() - updateTime < refreshTime){
            getFromSQlite()

        }else{
            getDataFromAPI()
        }

    }


    private fun getFromSQlite(){
        launch {

            val news = NewsDatabase(getApplication()).newsDao().getAllNews()
            newsDataFromSql.value = news
            Toast.makeText(getApplication(), "News From SQlite", Toast.LENGTH_SHORT).show()

        }
    }


    private fun getDataFromAPI(){

        newsLoading.value = true

        disposable.add(
            newsAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<NewsResponse>(){
                    override fun onSuccess(t: NewsResponse) {
                        storeInSQlite(t)
                        Toast.makeText(getApplication(), "News From API", Toast.LENGTH_SHORT).show()

                    }
                    override fun onError(e: Throwable) {
                        newsError.value = true
                        println(e.message)
                        newsLoading.value = false
                    }


                })
        )

    }

    private fun showNews(newsResponse: NewsResponse){
        newsData.value = newsResponse
        newsError.value = false
        newsLoading.value = false
    }


    private fun storeInSQlite(newsResponse: NewsResponse){
        launch {

            val dao = NewsDatabase(getApplication()).newsDao()
            dao.deleteAll()
            val listLong =  dao.insertAll(*newsResponse.articles!!.toTypedArray())

            var i = 0
            while (i < newsResponse.articles.size){

                newsResponse.articles[i].id = listLong[i].toInt()
                i += 1

            }
            showNews(newsResponse)
        }
        customPreferences.saveTime(System.nanoTime())

    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}
