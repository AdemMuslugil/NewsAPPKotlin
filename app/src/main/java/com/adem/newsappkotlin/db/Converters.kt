package com.adem.newsappkotlin.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.adem.newsappkotlin.model.Article
import com.adem.newsappkotlin.model.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source) : String{
        return source.name!!
    }

    @TypeConverter
    fun toSource(name: String) : Source{
        return Source(name)
    }


}