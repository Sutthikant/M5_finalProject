package com.example.finalproject_forreal.data.cb

import com.example.finalproject_forreal.data.model.CatItem

interface CatRresult {
    fun onDataFetchedSuccess(images: List<CatItem>)

    fun onDataFetchedFailed()
}