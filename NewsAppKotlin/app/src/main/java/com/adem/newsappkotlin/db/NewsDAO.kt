package com.adem.newsappkotlin.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.adem.newsappkotlin.model.Article
import com.adem.newsappkotlin.model.NewsResponse

@Dao
interface NewsDAO {
    @Insert
    suspend fun insertAll(vararg news: Article) : List<Long>

    @Query("SELECT * FROM article")
    suspend fun getAllNews(): List<Article>

    @Query("SELECT * FROM article WHERE id= :id")
    suspend fun getNews(id: Int): Article

    @Query("DELETE FROM article")
    suspend fun deleteAll()

}