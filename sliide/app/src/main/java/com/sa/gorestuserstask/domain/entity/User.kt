package com.sa.gorestuserstask.domain.entity

import androidx.annotation.StringRes
import com.sa.gorestuserstask.R

data class User(
    val id: Int = -1,
    val name: String = "",
    val email: String = "",
    val gender: Gender = Gender.Male,
    val status: Status = Status.Active,
    val createdAt: String = ""
)

enum class Gender(@StringRes val genderResId: Int) {
    Male(R.string.user_gender_male),
    Female(R.string.user_gender_female);
}

enum class Status(@StringRes val statusResId: Int) {
    Active(R.string.user_status_active),
    Inactive(R.string.user_status_inactive)
}
