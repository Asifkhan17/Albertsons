package com.demo.albertsons.ui

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.albertsons.R
import com.demo.albertsons.data.model.AcromineData
import com.demo.albertsons.repository.AcromineRepository
import com.demo.albertsons.util.NetworkHelper
import com.demo.albertsons.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SearchAcromineViewModel @Inject constructor(
    private val acromineRepository: AcromineRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    val searchAcromine: MutableLiveData<Resource> = MutableLiveData()

    @RequiresApi(Build.VERSION_CODES.M)
    fun searchForAcromine(searchQuery: String) = viewModelScope.launch {
        searchAcromine.postValue(Resource.Loading())
        try {
            if (networkHelper.isNetworkAvailable()) {
                val response = acromineRepository.searchAcromine(searchQuery)
                searchAcromine.postValue(handleSearchAcromineResponse(response))
            } else
                searchAcromine.postValue(Resource.Error("No Internet Connection Available"))
        } catch (ex: Exception) {
            when (ex) {
                is IOException -> searchAcromine.postValue(Resource.Error("Network Failure"))
                else -> searchAcromine.postValue(Resource.Error("Conversion Error"))
            }
        }
    }


    private fun handleSearchAcromineResponse(response: Response<List<AcromineData>>): Resource {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}