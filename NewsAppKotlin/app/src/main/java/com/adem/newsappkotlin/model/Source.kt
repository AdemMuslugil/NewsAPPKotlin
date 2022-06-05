package com.adem.newsappkotlin.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


data class Source(
    //@SerializedName("id")
    //val id: Any?,
    @SerializedName("name")
    val name: String?
)