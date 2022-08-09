package com.demo.albertsons.repository

import com.demo.albertsons.data.model.AcromineData
import com.demo.albertsons.data.remote.AcromineApi
import retrofit2.Response
import javax.inject.Inject

class AcromineRepository @Inject constructor(private val acromineApi: AcromineApi) {

    suspend fun searchAcromine(searchQuery: String): Response<List<AcromineData>> {
        return acromineApi.searchForAcromine(searchQuery)
    }
}