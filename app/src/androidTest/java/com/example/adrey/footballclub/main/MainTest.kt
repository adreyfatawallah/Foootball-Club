package com.example.adrey.footballclub.main

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.DataInteraction
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.*
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.example.adrey.footballclub.R.id.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.idling.CountingIdlingResource
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.Intents.intending
import android.support.test.espresso.intent.matcher.IntentMatchers.*
import android.support.test.espresso.web.assertion.WebViewAssertions
import android.support.test.espresso.web.sugar.Web
import android.support.test.espresso.web.webdriver.DriverAtoms
import android.support.test.espresso.web.webdriver.DriverAtoms.getText
import android.support.test.espresso.web.webdriver.Locator
import com.example.adrey.footballclub.ui.home.HomeActivity
import org.hamcrest.Matchers.*
import org.junit.Before


@RunWith(AndroidJUnit4::class)
class MainTest {

    @Rule
    @JvmField var matchRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun test1() {
        onView(allOf(withId(listMatch), isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(allOf(withId(listMatch), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))
        onView(withId(favorite))
                .check(matches(isDisplayed()))
        onView(withId(favorite))
                .perform(click())
        onView(withText("Added to favorite"))
                .check(matches(isDisplayed()))
    }

    @Test
    fun test2() {
        onView(withId(team)).check(matches(isDisplayed())).perform(click())
        onView(allOf(withId(rc_team), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(favorite))
                .check(matches(isDisplayed()))
        onView(withId(favorite))
                .perform(click())
        onView(withText("Added to favorite"))
                .check(matches(isDisplayed()))
    }

    @Test
    fun test3() {
        onView(withId(favorite)).check(matches(isDisplayed())).perform(click())
        onView(allOf(withId(listMatch), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(favorite))
                .check(matches(isDisplayed()))
        onView(withId(favorite))
                .perform(click())
        onView(withText("Removed to favorite"))
                .check(matches(isDisplayed()))
    }

    /**
     * Testing Adapter List View with Item TextView and ToggleButton
     */

//    fun onRow(string: String): DataInteraction = onData(hasEntry(equalTo(HomeActivity.ROW_TEXT), `is`(string)))
//
//    @Test
//    fun lastItem_NotDisplayed() {
//        onView(withText("item_text")).check(doesNotExist())
//    }
//
//    @Test
//    fun list_Scrolls() {
//        onRow("item_text").check(matches(isCompletelyDisplayed()))
//        onData(hasEntry(equalTo(HomeActivity.ROW_TEXT), `is`(LAST_ITEM_ID))).check(matches(isCompletelyDisplayed()))
//    }
//
//    @Test
//    fun row_Click() {
//        onRow("item_text").onChildView(withId(R.id.row_layout)).perform(click())
//
//        onView(withId(R.id.row_layout)).check(matches(withText("item_text")))
//    }
//
//    @Test
//    fun toggle_Click() {
//        onRow("item_text").onChildView(withId(R.id.toggle)).perform(click())
//        onRow("item_text").onChildView(withId(R.id.toggle)).check(matches(isChecked()))
//    }

    /**
     * Testing WebView
     */

//    @Test
//    fun typeTextInput_clickButton_SubmitForm() {
//        Web.onWebView()
//                .withElement(DriverAtoms.findElement(Locator.ID, "text_input"))
//                .perform(DriverAtoms.clearElement())
//                .perform(DriverAtoms.webKeys("Adrey"))
//                .withElement(DriverAtoms.findElement(Locator.ID, "submit"))
//                .perform(DriverAtoms.webClick())
//                .withElement(DriverAtoms.findElement(Locator.ID, "response"))
//                .check(WebViewAssertions.webMatches(getText(), containsString("Adrey")))
//    }

    /**
     * Testing Activity
     */

//    private val VALID_PHONE_NUMBER = "123-345-67879"
//    private val INTENT_DATA_PHONE_NUMBER = Uri.parse("tel:$VALID_PHONE_NUMBER")
//    private var PACKAGE_ANDROID_DIALER = "com.android.phone"
//
//    init {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
//            PACKAGE_ANDROID_DIALER = "com.android.server.telecom"
//    }
//
//    @Before
//    fun stubAllExternalIntents() {
//        intending(not(isInternal())).respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))
//    }
//
//    @Before
//    fun grantPhonePermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//            Instrumentation().uiAutomation.executeShellCommand(
//                    "pm grant " + InstrumentationRegistry.getTargetContext().packageName
//                            + " android.permission.CALL_PHONE")
//    }
//
//    @Test
//    fun typeNumber_ValidInput_InitiatesCall() {
//        onView(withId(R.id.edittext))
//                .perform(typeText(VALID_PHONE_NUMBER), ViewActions.closeSoftKeyboard())
//        onView(withId(R.id.call)).perform(click())
//
//        intended(allOf(
//                hasAction(Intent.ACTION_CALL),
//                hasData(INTENT_DATA_PHONE_NUMBER),
//                toPackage(PACKAGE_ANDROID_DIALER)))
//    }

    /**
     * Backround Process
     */

    val idlingResource = CountingIdlingResource("DATA LOADER")

    fun asyncTask() {
        // before or background process
        idlingResource.increment()

        // after or result background proses
        idlingResource.decrement()
    }
}