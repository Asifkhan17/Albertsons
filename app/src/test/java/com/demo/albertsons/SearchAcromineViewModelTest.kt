package com.demo.albertsons

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.demo.albertsons.data.model.AcromineData
import com.demo.albertsons.data.model.Lf
import com.demo.albertsons.repository.AcromineRepository
import com.demo.albertsons.ui.SearchAcromineViewModel
import com.demo.albertsons.util.NetworkHelper
import com.demo.albertsons.util.Resource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Response
import javax.inject.Inject


@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class)
class SearchAcromineViewModelTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    private lateinit var classToTest: SearchAcromineViewModel

    @Mock
    @Inject
    lateinit var acromineRepository: AcromineRepository

    @Mock
    @Inject
    lateinit var networkHelper: NetworkHelper

    @Mock
    lateinit var viewResourceObserver: Observer<Resource>


    @Before
    fun setUp() {
        hiltRule.inject()
        MockitoAnnotations.openMocks(this)
        classToTest = SearchAcromineViewModel(acromineRepository, networkHelper)
    }

    @After
    fun tearDown() {
        classToTest.searchAcromine.removeObserver(viewResourceObserver)
    }


    @Test
    fun `when fetching results network not available`() = runBlocking {
        classToTest.searchAcromine.observeForever(viewResourceObserver)
        `when`(networkHelper.isNetworkAvailable()).thenReturn(false)
        classToTest.searchForAcromine("hmm")
        verify(viewResourceObserver, times(1)).onChanged(classToTest.searchAcromine.value)
        assertNotNull(classToTest.searchAcromine.value)
        assertEquals("No Internet Connection Available",classToTest.searchAcromine.value?.message)
    }

    @Test
    fun `when fetching results ok then return success with list data`() = runBlocking{
        val mockList: List<AcromineData> = provideMockList()
        val response = Response.success(mockList)
        classToTest.searchAcromine.observeForever(viewResourceObserver)
        `when`(networkHelper.isNetworkAvailable()).thenReturn(true)
        `when`(acromineRepository.searchAcromine("hmm")).thenReturn(response)
        classToTest.searchForAcromine("hmm")
        verify(viewResourceObserver).onChanged(classToTest.searchAcromine.value)
        val result: List<AcromineData> =
            classToTest.searchAcromine.value?.data as List<AcromineData>
        assertNotNull(classToTest.searchAcromine.value)
        assertTrue(response.isSuccessful)
        assertEquals(mockList.size, result.size)
        assertEquals(mockList[0].sf, result[0].sf)
        assertEquals(mockList[0].lfs!![0].lf, result[0].lfs!![0].lf)
    }

    @Test
    fun `when fetching results ok then return success with empty list`() = runBlocking {
        val mockList= emptyList<AcromineData>()
        val response = Response.success(mockList)
        classToTest.searchAcromine.observeForever(viewResourceObserver)
        `when`(networkHelper.isNetworkAvailable()).thenReturn(true)
        `when`(acromineRepository.searchAcromine("hmm")).thenReturn(response)
        classToTest.searchForAcromine("hmm")
        verify(viewResourceObserver).onChanged(classToTest.searchAcromine.value)
        assertNotNull(classToTest.searchAcromine.value)
        val result: List<AcromineData> =
            classToTest.searchAcromine.value?.data as List<AcromineData>
        assertTrue(response.isSuccessful)
        assertEquals(mockList.size, result.size)
        assertTrue(result.isEmpty())
    }

    @Test
    fun `when fetching results then return an error`() = runBlocking {
        val errorResponse =
            "{\n" +
                    "  \"type\": \"error\",\n" +
                    "  \"message\": \"something went wrong.\"\n"+ "}"
        val errorResponseBody = errorResponse.toResponseBody("application/json".toMediaTypeOrNull())
        val mockResponse = Response.error<List<AcromineData>>(502, errorResponseBody)
        classToTest.searchAcromine.observeForever(viewResourceObserver)
        `when`(networkHelper.isNetworkAvailable()).thenReturn(true)
        `when`(acromineRepository.searchAcromine("hmm")).thenReturn(mockResponse)
        classToTest.searchForAcromine("hmm")
        verify(viewResourceObserver).onChanged(classToTest.searchAcromine.value)
        assertNotNull(classToTest.searchAcromine.value)
        assertNull(classToTest.searchAcromine.value?.data)
        assertEquals("Response.error()",classToTest.searchAcromine.value?.message)

    }

    @Test
    fun `when calling for results then return loading`() = runBlocking {
        classToTest.searchAcromine.observeForever(viewResourceObserver)
        classToTest.searchForAcromine("hmm")
        verify(viewResourceObserver, times(1)).onChanged(classToTest.searchAcromine.value)
    }

    private fun provideMockList(): List<AcromineData> {
        val item1 = AcromineData()
        item1.sf = "hmm"
        val lfs=Lf()
        lfs.lf="heavy meromyosin"
        item1.lfs= listOf(lfs)
        val listItemOfMockData = mutableListOf<AcromineData>()
        listItemOfMockData.add(item1)
        return listItemOfMockData
    }

}