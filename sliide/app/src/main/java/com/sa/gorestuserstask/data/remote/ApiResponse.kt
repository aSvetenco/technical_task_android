package com.sa.gorestuserstask.data.remote


abstract class ApiResponse(
    val meta: Meta? = null,
)

class UserApiResponse(
    val data: List<UserApiModel> = listOf()
) : ApiResponse()


class AddUserApiResponse(
    val data: UserApiModel = UserApiModel()
) : ApiResponse()

data class Meta(
    val pagination: Pagination = Pagination()
)

data class Pagination(
    val total: Int = 0,
    val pages: Int = 0,
    val page: Int = 0,
    val limit: Int = 0
)

data class UserApiModel(
    val id: Int = -1,
    val name: String = "",
    val email: String = "",
    val gender: String = "",
    val status: String = ""
)

data class UserApiRequest(
    val name: String,
    val email: String,
    val gender: String,
    val status: String
)

