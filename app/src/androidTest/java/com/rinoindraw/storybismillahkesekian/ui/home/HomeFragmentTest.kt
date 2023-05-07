package com.rinoindraw.storybismillahkesekian.ui.home

import androidx.paging.ExperimentalPagingApi
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.rinoindraw.storybismillahkesekian.utils.JsonConverter
import com.rinoindraw.storybismillahkesekian.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.rinoindraw.storybismillahkesekian.R
import com.rinoindraw.storybismillahkesekian.data.remote.retrofit.ApiConfig.Companion.API_BASE_URL_MOCK
import com.rinoindraw.storybismillahkesekian.utils.EspressoIdlingResource

@MediumTest
@ExperimentalPagingApi
@HiltAndroidTest
class HomeFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private val mockWebServer = MockWebServer()

    @Before
    fun setup() {
        mockWebServer.start(8080)
        API_BASE_URL_MOCK = "http://127.0.0.1:8080/"
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun launchHomeFragment_Success() {
        launchFragmentInHiltContainer<HomeFragment>()

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("success_response.json"))
        mockWebServer.enqueue(mockResponse)

        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_stories)).check(matches(isDisplayed()))

        onView(withText("Dimas")).check(matches(isDisplayed()))
    }

    @Test
    fun launchHomeFragment_Empty() {
        launchFragmentInHiltContainer<HomeFragment>()

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("success_response_empty.json"))
        mockWebServer.enqueue(mockResponse)

        onView(withId(R.id.iv_not_found_error)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_not_found_error)).check(matches(isDisplayed()))
    }

    @Test
    fun launchHomeFragment_Failed() {
        launchFragmentInHiltContainer<HomeFragment>()

        val mockResponse = MockResponse()
            .setResponseCode(500)
        mockWebServer.enqueue(mockResponse)

        onView(withId(R.id.iv_not_found_error)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_not_found_error)).check(matches(isDisplayed()))
    }

}