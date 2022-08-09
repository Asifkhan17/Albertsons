package com.demo.albertsons

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.demo.albertsons.data.remote.AcromineApi
import com.demo.albertsons.repository.AcromineRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.net.HttpURLConnection
import javax.inject.Inject

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class)
class AcromineRepositoryTest : MockWebServerBaseTest() {

    @get:Rule
    var hiltrule = HiltAndroidRule(this)

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var acromineRepository: AcromineRepository
    @Inject
    lateinit var apiService: AcromineApi

    override fun isMockServerEnabled() = true

    @Before
    fun start() {
        hiltrule.inject()
        MockitoAnnotations.openMocks(this)
        acromineRepository = AcromineRepository(apiService)
    }

    @Test
    fun `provide response ok when fetching results then return a list with data`() {
        runBlocking {
            mockHttpResponse(apiResponse(), HttpURLConnection.HTTP_OK)
            val apiResponse = acromineRepository.searchAcromine("hmm")
            Assert.assertNotNull(apiResponse)
            Assert.assertTrue(apiResponse.isSuccessful)
            Assert.assertEquals(HttpURLConnection.HTTP_OK,apiResponse.code())
            Assert.assertEquals(1,apiResponse.body()?.size)
        }
    }

    @Test
    fun `provide response ok when fetching empty results then return an empty list`() {
        runBlocking {
            mockHttpResponse(apiResponseEmpty(), HttpURLConnection.HTTP_OK)
            val apiResponse = acromineRepository.searchAcromine("z")
            assertNotNull(apiResponse)
            assertTrue(apiResponse.isSuccessful)
            assertEquals(HttpURLConnection.HTTP_OK,apiResponse.code())
            assertEquals(0,apiResponse.body()?.size)
        }
    }

    private fun apiResponse(): String {
        return "\"[{\\\"sf\\\":\\\"HMM\\\",\\\"lfs\\\":[{\\\"lf\\\":\\\"heavymeromyosin\\\",\\\"freq\\\":267,\\\"since\\\":1971,\\\"vars\\\":[{\\\"lf\\\":\\\"heavymeromyosin\\\",\\\"freq\\\":244,\\\"since\\\":1971},{\\\"lf\\\":\\\"Heavymeromyosin\\\",\\\"freq\\\":12,\\\"since\\\":1975},{\\\"lf\\\":\\\"H-meromyosin\\\",\\\"freq\\\":5,\\\"since\\\":1975},{\\\"lf\\\":\\\"heavy-meromyosin\\\",\\\"freq\\\":4,\\\"since\\\":1977},{\\\"lf\\\":\\\"heavymeromyosin\\\",\\\"freq\\\":1,\\\"since\\\":1976},{\\\"lf\\\":\\\"H-Meromyosin\\\",\\\"freq\\\":1,\\\"since\\\":1976}]},{\\\"lf\\\":\\\"hiddenMarkovmodel\\\",\\\"freq\\\":245,\\\"since\\\":1990,\\\"vars\\\":[{\\\"lf\\\":\\\"hiddenMarkovmodel\\\",\\\"freq\\\":148,\\\"since\\\":1992},{\\\"lf\\\":\\\"HiddenMarkovModel\\\",\\\"freq\\\":29,\\\"since\\\":1993},{\\\"lf\\\":\\\"hiddenMarkovmodels\\\",\\\"freq\\\":26,\\\"since\\\":1995},{\\\"lf\\\":\\\"HiddenMarkovModels\\\",\\\"freq\\\":13,\\\"since\\\":2001},{\\\"lf\\\":\\\"HiddenMarkovmodel\\\",\\\"freq\\\":9,\\\"since\\\":1994},{\\\"lf\\\":\\\"HiddenMarkovmodels\\\",\\\"freq\\\":6,\\\"since\\\":1995},{\\\"lf\\\":\\\"HiddenMarkovModeling\\\",\\\"freq\\\":2,\\\"since\\\":2007},{\\\"lf\\\":\\\"hiddenMarkovModel\\\",\\\"freq\\\":2,\\\"since\\\":2008},{\\\"lf\\\":\\\"HiddenMarkovmodeling\\\",\\\"freq\\\":2,\\\"since\\\":2000},{\\\"lf\\\":\\\"hiddenMarkovmodeling\\\",\\\"freq\\\":2,\\\"since\\\":1990},{\\\"lf\\\":\\\"Hidden-MarkovModel\\\",\\\"freq\\\":1,\\\"since\\\":2008},{\\\"lf\\\":\\\"HiddenMarkovmodelling\\\",\\\"freq\\\":1,\\\"since\\\":1990},{\\\"lf\\\":\\\"hiddenmarkovmodels\\\",\\\"freq\\\":1,\\\"since\\\":2000},{\\\"lf\\\":\\\"hiddenmarkovmodel\\\",\\\"freq\\\":1,\\\"since\\\":2005},{\\\"lf\\\":\\\"hidden-Markov-model\\\",\\\"freq\\\":1,\\\"since\\\":1996},{\\\"lf\\\":\\\"Hidden-Markov-Model\\\",\\\"freq\\\":1,\\\"since\\\":2004}]},{\\\"lf\\\":\\\"hexamethylmelamine\\\",\\\"freq\\\":55,\\\"since\\\":1976,\\\"vars\\\":[{\\\"lf\\\":\\\"hexamethylmelamine\\\",\\\"freq\\\":45,\\\"since\\\":1977},{\\\"lf\\\":\\\"Hexamethylmelamine\\\",\\\"freq\\\":10,\\\"since\\\":1976}]},{\\\"lf\\\":\\\"highmolecularmass\\\",\\\"freq\\\":44,\\\"since\\\":1982,\\\"vars\\\":[{\\\"lf\\\":\\\"highmolecularmass\\\",\\\"freq\\\":26,\\\"since\\\":1982},{\\\"lf\\\":\\\"high-molecular-mass\\\",\\\"freq\\\":17,\\\"since\\\":1991},{\\\"lf\\\":\\\"Highmolecularmass\\\",\\\"freq\\\":1,\\\"since\\\":2001}]},{\\\"lf\\\":\\\"humanmalignantmesothelioma\\\",\\\"freq\\\":17,\\\"since\\\":1986,\\\"vars\\\":[{\\\"lf\\\":\\\"humanmalignantmesothelioma\\\",\\\"freq\\\":4,\\\"since\\\":1990},{\\\"lf\\\":\\\"humanmonocyte-derivedmacrophages\\\",\\\"freq\\\":3,\\\"since\\\":1991},{\\\"lf\\\":\\\"humanmonocyte-macrophages\\\",\\\"freq\\\":3,\\\"since\\\":1990},{\\\"lf\\\":\\\"Humanmalignantmesothelioma\\\",\\\"freq\\\":3,\\\"since\\\":2005},{\\\"lf\\\":\\\"Humanmonocyte-derivedmacrophages\\\",\\\"freq\\\":2,\\\"since\\\":1986},{\\\"lf\\\":\\\"humanmonocyte-macrophage\\\",\\\"freq\\\":2,\\\"since\\\":1999}]},{\\\"lf\\\":\\\"hydroxymethylmexiletine\\\",\\\"freq\\\":8,\\\"since\\\":1990,\\\"vars\\\":[{\\\"lf\\\":\\\"hydroxymethylmexiletine\\\",\\\"freq\\\":8,\\\"since\\\":1990}]},{\\\"lf\\\":\\\"HomeManagementofMalaria\\\",\\\"freq\\\":5,\\\"since\\\":2006,\\\"vars\\\":[{\\\"lf\\\":\\\"HomeManagementofMalaria\\\",\\\"freq\\\":3,\\\"since\\\":2006},{\\\"lf\\\":\\\"homemanagementofmalaria\\\",\\\"freq\\\":2,\\\"since\\\":2007}]},{\\\"lf\\\":\\\"6a-hydroxymaackiain3-O-methyltransferase\\\",\\\"freq\\\":3,\\\"since\\\":1997,\\\"vars\\\":[{\\\"lf\\\":\\\"6a-hydroxymaackiain3-O-methyltransferase\\\",\\\"freq\\\":3,\\\"since\\\":1997}]}]}]\""
    }
    private fun apiResponseEmpty():String{
        return ""
    }
}