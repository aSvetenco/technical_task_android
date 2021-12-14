package com.sa.gorestuserstask.data.remote.error

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.sa.gorestuserstask.data.remote.ErrorResponse
import timber.log.Timber
import java.lang.reflect.Type

class ErrorResponseDeserializer : JsonDeserializer<List<ErrorResponse>> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): List<ErrorResponse> =
        try {
            val errors = mutableListOf<ErrorResponse>()
            if (json != null) {
                val jsonObject = json.asJsonObject
                for (element in jsonObject.entrySet()) {
                    if (element.key == DATA) {
                        if (element.value.isJsonObject) {
                            errors.add(mapToErrorResponse(element.value.asJsonObject))
                            continue
                        }

                        if (element.value.isJsonArray) {
                            val data = element.value.asJsonArray
                                .map { it.asJsonObject }
                                .map(::mapToErrorResponse)
                            errors += data
                        }
                    }
                }
            }
            errors
        } catch (e: Exception) {
            Timber.e(e)
            listOf()
        }

    private fun mapToErrorResponse(json: JsonObject): ErrorResponse =
        ErrorResponse(
            field = json[FIELD]?.asString,
            message = json[MESSAGE]?.asString
        )


    private companion object {
        const val DATA = "data"
        const val FIELD = "field"
        const val MESSAGE = "message"
    }
}
