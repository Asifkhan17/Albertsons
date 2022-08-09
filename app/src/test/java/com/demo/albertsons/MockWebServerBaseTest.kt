package com.demo.albertsons

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before

abstract class MockWebServerBaseTest {

    private lateinit var mockServer: MockWebServer

    @Before
    open fun setUp() {
        this.configureMockServer()
    }

    @After
    open fun tearDown() {
        this.stopMockServer()
    }

    abstract fun isMockServerEnabled(): Boolean

    open fun configureMockServer() {
        if (isMockServerEnabled()) {
            mockServer = MockWebServer()
            mockServer.start()
        }
    }

    open fun stopMockServer() {
        if (isMockServerEnabled()) {
            mockServer.shutdown()
        }
    }

    open fun mockHttpResponse(fileName: String, responseCode: Int) =
        mockServer.enqueue(MockResponse().setResponseCode(responseCode).setBody(fileName))

    open fun mockHttpResponse(responseCode: Int) =
        mockServer.enqueue(MockResponse().setResponseCode(responseCode))

}