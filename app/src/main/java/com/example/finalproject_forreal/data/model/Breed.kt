package com.example.finalproject_forreal.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Breed(
    val country_code: String,
    val country_codes: String,
    val id: String,
    val life_span: String,
    val name: String,
    val origin: String,
    val temperament: String,
    val weight: Weight,
    val wikipedia_url: String
) :Parcelable