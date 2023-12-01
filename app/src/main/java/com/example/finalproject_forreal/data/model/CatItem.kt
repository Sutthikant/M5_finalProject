package com.example.finalproject_forreal.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CatItem(
    val breeds: List<Breed>?,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
) :Parcelable