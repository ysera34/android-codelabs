package org.inframincer.twoactivitiesespresso

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4

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
class ActivityInputOutputTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("org.inframincer.twoactivitiesespresso", appContext.packageName)
    }

    @Test
    fun activityLaunch() {
        Espresso.onView(ViewMatchers.withId(R.id.button_main)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.text_header)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.button_second)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.text_header_reply))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun textInputOutput() {
        val message = "This is a test."
        Espresso.onView(ViewMatchers.withId(R.id.editText_main)).perform(ViewActions.typeText(message))
        Espresso.onView(ViewMatchers.withId(R.id.button_main)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.text_message))
            .check(ViewAssertions.matches(ViewMatchers.withText(message)))
    }
}
