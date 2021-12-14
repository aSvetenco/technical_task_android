package com.sa.gorestuserstask.data.remote.error

import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.sa.gorestuserstask.data.remote.ErrorResponse
import junit.framework.TestCase.*
import org.junit.Test

class ErrorResponseDeserializerTest {

    private val type = object : TypeToken<List<ErrorResponse>>() {}.type

    private var gson = GsonBuilder()
        .registerTypeAdapter(type, ErrorResponseDeserializer())
        .create()

    @Test
    fun `deserializer return error list with size one if errorBody do not has array`() {
        val response = ErrorResponse(null, "Authentication failed")
        val errorBody = """
           {
           	"meta": null,
           	"data": {
           		"message": "Authentication failed"
           	}
           }
       """.trimIndent()

        val errors = try {
            gson.fromJson(errorBody, object : TypeToken<List<ErrorResponse>>() {}.type)
        } catch (expected: JsonSyntaxException) {
            listOf<ErrorResponse>()
        }

        assertFalse(errors.isEmpty())
        assertTrue(errors.size == 1)
        assertEquals(response, errors[0])
    }

    @Test
    fun `deserializer return error list of errors if errorBody has array`() {
        val expectedList = listOf(
            ErrorResponse("email", "can't be blank"),
            ErrorResponse("name", "can't be blank")
        )
        val errorBody = """
            {
            	"meta": null,
            	"data": [{
            		"field": "email",
            		"message": "can't be blank"
            	}, {
            		"field": "name",
            		"message": "can't be blank"
            	}]
            }
       """.trimIndent()

        val errors = try {
            gson.fromJson(errorBody, object : TypeToken<List<ErrorResponse>>() {}.type)
        } catch (expected: JsonSyntaxException) {
            listOf<ErrorResponse>()
        }

        assertFalse(errors.isEmpty())
        assertTrue(errors.size == 2)
        assertEquals(expectedList[0], errors[0])
        assertEquals(expectedList[1], errors[1])
    }

    @Test
    fun `deserializer return empty list if errorBody has invalid format`() {

        val errorBody = """
            {
            	"error": [{
            		"field": "email",
            		"message": "can't be blank"
            	}, {
            		"field": "name",
            		"message": "can't be blank"
            	}]
            }
       """.trimIndent()

        val errors = try {
            gson.fromJson(errorBody, object : TypeToken<List<ErrorResponse>>() {}.type)
        } catch (expected: JsonSyntaxException) {
            listOf(
                ErrorResponse("email", "can't be blank"),
                ErrorResponse("name", "can't be blank")
            )
        }

        assertTrue(errors.isEmpty())
    }
}
