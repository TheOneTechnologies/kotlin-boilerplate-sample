package com.theonetech.kotlin.presentation.viewmodel

import junit.framework.Assert.assertFalse
import org.junit.Test
/*
  Created by Amit on 13,Aug,2020
 */
class LoginViewModelTest {

    val username = "YourUserName"
    val password = "YourPassword"

    @Test
    fun testCheckValidations() {
        if (username.isEmpty()) {
            assertFalse("username is empty", true)

        }
        if (password.isEmpty()) {
            assertFalse("password is empty", true)
        }
        if (password.length < 8) {
            assertFalse("password must be between 8 and 40 characters", true)

        }
        if (password.length > 40) {
            assertFalse("password must be between 8 and 40 characters", true)

        }
    }

}