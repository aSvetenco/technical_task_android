package com.sa.gorestuserstask.data.remote.error

import okhttp3.ResponseBody

class ApiException(
        val errorBody: ResponseBody?,
        val httpCode: Int
) : RuntimeException()
