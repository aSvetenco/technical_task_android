package com.sa.gorestuserstask.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface GorestUsersApiService {

    @GET("$VERSION/users")
    suspend fun getUsers(@Query("page") pageNumber: Int? = null): Response<UserApiResponse>

    @POST("$VERSION/users")
    suspend fun addUser(@Body user: UserApiRequest): Response<AddUserApiResponse>

    @DELETE("$VERSION/users/{id}")
    suspend fun deleteUser(@Path("id") id: Int): Response<Unit>

    private companion object {
        private const val VERSION = "v1"
    }
}
