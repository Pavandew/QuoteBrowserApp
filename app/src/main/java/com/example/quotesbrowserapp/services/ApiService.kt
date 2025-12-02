package com.example.quotesbrowserapp.services

import com.example.quotesbrowserapp.model.PicsumItem
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/list")
    suspend fun getPicsSumList(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ) : List<PicsumItem>

}