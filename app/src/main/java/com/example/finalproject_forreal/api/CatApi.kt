package com.example.finalproject_forreal.api

import com.example.finalproject_forreal.data.model.CatDetails
import com.example.finalproject_forreal.data.model.CatItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface CatApi {

    @Headers("x-api-key: live_TgeXVVJ3EYD8nA0Kq3Qhk3e8oo6fIOQfLCzum61LoGRDjEVZwnlKz1CL2VG50f6I")
    @GET("search")
    fun fetchPhotos(@Query(value = "limit") limit: Int) : Call<List<CatItem>>

    @Headers("x-api-key: live_TgeXVVJ3EYD8nA0Kq3Qhk3e8oo6fIOQfLCzum61LoGRDjEVZwnlKz1CL2VG50f6I")
    @GET("{id}")
    fun fetchPhotosDetails(@Path(value = "id") id: String) : Call<CatDetails>
}