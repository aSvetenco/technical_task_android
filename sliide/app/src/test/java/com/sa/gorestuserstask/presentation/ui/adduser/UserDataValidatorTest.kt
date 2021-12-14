package com.sa.gorestuserstask.presentation.ui.adduser

import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test


class UserDataValidatorTest {

    private val validator = UserDataValidator()

    @Test
    fun `isValid return false if user name is empty`() {
        val userName = ""
        val email = "email@email.com"

        val userData = UserDataValidator.ValidationData(userName, email)

        assertFalse(validator.isValid(userData))
    }

    @Test
    fun `isValid return false if user name is blank`() {
        val userName = "  "
        val email = "email@email.com"

        val userData = UserDataValidator.ValidationData(userName, email)

        assertFalse(validator.isValid(userData))
    }

    @Test
    fun `isValid return false if user email is empty`() {
        val userName = "John"
        val email = ""

        val userData = UserDataValidator.ValidationData(userName, email)

        assertFalse(validator.isValid(userData))
    }

    @Test
    fun `isValid return false if user email has invalid format`() {
        val userName = "John"
        val email = "email.email"

        val userData = UserDataValidator.ValidationData(userName, email)

        assertFalse(validator.isValid(userData))
    }

    @Test
    fun `isValid return true if user name is not empty or blank and user email is valid`() {
        val userName = "John"
        val email = "email@email.com"

        val userData = UserDataValidator.ValidationData(userName, email)

        assertTrue(validator.isValid(userData))
    }
}
