package com.adem.newsappkotlin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.adem.newsappkotlin.model.Article
import com.adem.newsappkotlin.model.NewsResponse

@Database(entities = [Article::class], version = 1)
@TypeConverters(Converters::class)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao() : NewsDAO


    companion object {

        @Volatile private var instance: NewsDatabase? = null

        private val LOCK = Any()

        operator fun invoke(contex: Context) = instance ?: synchronized(LOCK){
            instance ?: makeDatabase(contex).also {

                instance = it
            }
        }


        private fun makeDatabase(contex : Context) = Room.databaseBuilder(contex.applicationContext,NewsDatabase::class.java,
            "newsdatabase")
            .build()

    }
}