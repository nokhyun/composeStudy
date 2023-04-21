package com.example.ui

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainUI {

    @get:Rule var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun textEquals(){
        onView(withId(R.id.tvHello)).check(matches(withText("Hello")))
    }
}