package com.example.baatutechdemoapp

import retrofit2.Call
import retrofit2.http.GET

interface Api {

    @GET(ApiConstants.API_END_POINT)
    fun getDataList(
    ): Call<ArrayList<User>>
}