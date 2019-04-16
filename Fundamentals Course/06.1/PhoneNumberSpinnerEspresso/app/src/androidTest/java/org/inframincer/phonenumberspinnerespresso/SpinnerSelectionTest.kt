package org.inframincer.phonenumberspinnerespresso

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4

import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.containsString

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class SpinnerSelectionTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("org.inframincer.phonenumberspinnerespresso", appContext.packageName)
    }

    @Test
    fun iterateSpinnerItems() {
        val labels = activityRule.activity.resources.getStringArray(R.array.labels_array)

        // Iterate through the spinner array of items.
        labels.forEach {

            // Find the spinner and click on it.
            onView(withId(R.id.labelSpinner)).perform(click())
            // Find the spinner item and click on it.
            onData(`is`(it)).perform(click())
            // Find the text view and check that the spinner item is part of the string.
            onView(withId(R.id.phoneNumberTextView)).check(matches(withText(containsString(it))))
        }
    }
}
