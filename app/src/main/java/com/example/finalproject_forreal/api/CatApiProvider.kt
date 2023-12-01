package com.example.finalproject_forreal.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.Callback
import retrofit2.Response
import com.example.finalproject_forreal.data.cb.CatRresult
import com.example.finalproject_forreal.data.cb.CatRresult2
import com.example.finalproject_forreal.data.model.CatDetails
import com.example.finalproject_forreal.data.model.CatItem
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class CatApiProvider {

    private val BASE_URL = "https://api.thecatapi.com/v1/images/"

    private val retrofit by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
//
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()


        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create<CatApi>()
    }

    fun fetchImages(cb: CatRresult, limit: Int) {
        retrofit.fetchPhotos(limit).enqueue(object : Callback<List<CatItem>> {
            override fun onResponse(
                call: Call<List<CatItem>>,
                response: Response<List<CatItem>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    cb.onDataFetchedSuccess(response.body()!!)
                } else {
                    cb.onDataFetchedFailed()
                }
            }

            override fun onFailure(call: Call<List<CatItem>>, t: Throwable) {
                cb.onDataFetchedFailed()
            }
        })
    }

    fun fetchImagesDetails(cb: CatRresult2, id: String) {
        retrofit.fetchPhotosDetails(id).enqueue(object : Callback<CatDetails> {
            override fun onResponse(
                call: Call<CatDetails>,
                response: Response<CatDetails>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    cb.onDataFetchedSuccess(response.body()!!)
                } else {
                    cb.onDataFetchedFailed()
                }
            }

            override fun onFailure(call: Call<CatDetails>, t: Throwable) {
                cb.onDataFetchedFailed()
            }
        })
    }
}