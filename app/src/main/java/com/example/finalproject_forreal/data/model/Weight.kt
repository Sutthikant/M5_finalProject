package com.example.finalproject_forreal.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weight(
    val imperial: String,
    val metric: String
) :Parcelable