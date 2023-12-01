package com.example.finalproject_forreal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject_forreal.api.CatApiProvider
import com.example.finalproject_forreal.data.cb.CatRresult
import com.example.finalproject_forreal.data.cb.CatRresult2
import com.example.finalproject_forreal.data.model.CatDetails
import com.example.finalproject_forreal.data.model.CatItem

private const val TAG = "CatViewModel"
class CatViewModel : ViewModel(), CatRresult {

    private val _items = MutableLiveData<List<CatItem>>()
    val items: LiveData<List<CatItem>> = _items

    private val provider by lazy {
        CatApiProvider()
    }

    fun fetchImages() {
        provider.fetchImages(this, 20)
    }

    override fun onDataFetchedSuccess(images: List<CatItem>) {
        Log.d(TAG, "onDataFetchedSuccess | Received ${images.size} images")
        _items.value = images
    }

    override fun onDataFetchedFailed() {
        Log.e(TAG, "onDataFetchedFailed | Unable to retrieve images")
    }
}

class CatViewModel2 : ViewModel(), CatRresult2 {

    private val _items = MutableLiveData<CatDetails>()
    val items: LiveData<CatDetails> = _items

    private val provider by lazy {
        CatApiProvider()
    }

    fun fetchImagesDetails(id: String) {
        provider.fetchImagesDetails(this, id)
    }

    override fun onDataFetchedSuccess(images: CatDetails) {
        Log.d(TAG, "onDataFetchedSuccess | Received ${images} images")
        _items.value = images
    }

    override fun onDataFetchedFailed() {
        Log.e(TAG, "onDataFetchedFailed | Unable to retrieve images details")
    }
}

