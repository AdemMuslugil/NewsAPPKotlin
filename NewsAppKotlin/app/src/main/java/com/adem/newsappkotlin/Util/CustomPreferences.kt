package com.adem.newsappkotlin.Util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class CustomPreferences {

    companion object{
        private val PREFERENCES_TIME = "preferences_time"
        private var sharedPreferences : SharedPreferences? = null

        @Volatile private var instance : CustomPreferences? = null

        private val LOCK = Any()
        operator fun invoke(context: Context) : CustomPreferences = instance ?: synchronized(LOCK){

            instance ?: makeSharedPreferences(context).also {

                instance = it

            }

        }

        private fun makeSharedPreferences(context: Context) : CustomPreferences{

            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return CustomPreferences()

        }

    }

    fun saveTime(time: Long){

        sharedPreferences?.edit(commit = true){
            putLong(PREFERENCES_TIME,time)

        }

    }

    fun getTime() : Long{
      return sharedPreferences!!.getLong(PREFERENCES_TIME,0)
    }

}