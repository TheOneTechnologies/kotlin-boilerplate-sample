package com.theonetech.kotlin.presentation.view

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.theonetech.kotlin.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/*
  Created by Amit on 14,Aug,2020
 */
@RunWith(AndroidJUnit4::class)
class DashboardActivityTest {
    @Rule
    @JvmField
    var activityDashboard = ActivityTestRule<DashboardActivity>(
        DashboardActivity::class.java
    )

    //test for recyclerview visibility in dashboard
    @Test
    fun testRecyclerviewIsVisible() {
        onView(withId(R.id.recyclerview_dashboard))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    //test for recyclerview item click for open homework screen
    @Test
    fun testRecyclerviewItemClickHomework() {
        onView(withId(R.id.recyclerview_dashboard)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
    }

    //test for recyclerview item click for open photo gallery screen
    @Test
    fun testRecyclerviewItemClickPhotoGallery() {
        onView(withId(R.id.recyclerview_dashboard)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click())
        )
    }


}
