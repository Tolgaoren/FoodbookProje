package com.toren.foodbookapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Yemek(
    val yemekIsmi: String = "",
    val aciklama: String = "",
    val malzemeler: ArrayList<String> = arrayListOf(),
    val malzemelerDetayli: ArrayList<String> = arrayListOf(),
    val hazirlanis: String = "",
    val kategori: String = "",
    val imgUrl: String = "",
    val user: String = "",
    val username: String = "",
    val likes: ArrayList<String> = arrayListOf(),
    val comments: ArrayList<String> = arrayListOf(),
    val id: String = ""
) : Parcelable
