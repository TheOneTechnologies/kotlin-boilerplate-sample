package com.theonetech.kotlin.domain.utils

import junit.framework.Assert.assertEquals
import org.junit.Test

/*
  Created by Amit on 13,Aug,2020
 */
class UtilsTest {
    val testDate = "2020-08-11T00:00:00"

    @Test
    fun testGetDayFromString() {
        assertEquals("11", Utils.getDayFromString(testDate))
    }

    @Test
    fun testGetMonthYearFromString() {
        assertEquals("Aug-20", Utils.getMonthYearFromString(testDate))
    }
}
