package com.demo.albertsons.data.remote

import com.demo.albertsons.data.model.AcromineData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AcromineApi {
    @GET("software/acromine/dictionary.py")
    suspend fun searchForAcromine(
        @Query("sf") searchQuery: String
    ): Response<List<AcromineData>>
}