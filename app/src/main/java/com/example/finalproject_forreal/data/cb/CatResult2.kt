package com.example.finalproject_forreal.data.cb

import com.example.finalproject_forreal.data.model.CatDetails

interface CatRresult2 {
    fun onDataFetchedSuccess(images: CatDetails)

    fun onDataFetchedFailed()
}