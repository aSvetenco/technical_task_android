package com.sa.gorestuserstask.presentation.ui.adduser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sa.gorestuserstask.domain.entity.User
import com.sa.gorestuserstask.domain.usecase.AddUserUseCase
import com.sa.gorestuserstask.domain.usecase.Output
import com.sa.gorestuserstask.presentation.utils.DomainErrorMapper
import com.sa.gorestuserstask.presentation.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddUserViewModel @Inject constructor(
    private val addUserUseCase: AddUserUseCase,
    private val errorMapper: DomainErrorMapper,
    private val validator: UserDataValidator,
) : ViewModel(), UserDataValidator.UserValidationListener {

    private val invalidUserNameMLD = MutableLiveData<Int>()
    val invalidUserName: LiveData<Int> = invalidUserNameMLD

    private val invalidUserEmailMLD = MutableLiveData<Int>()
    val invalidUserEmail: LiveData<Int> = invalidUserEmailMLD

    private val isLoadingMLD = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = isLoadingMLD

    val onErrorLE = SingleLiveEvent<String>()
    val onSuccessLE = SingleLiveEvent<Unit>()

    init {
        validator.listener = this
    }

    fun addUser(user: User) {
        if (validator.isValid(UserDataValidator.ValidationData(user.name, user.email))) {
            isLoadingMLD.value = true
            viewModelScope.launch {
                val result = addUserUseCase.invoke(user)
                isLoadingMLD.value = false
                when (result) {
                    is Output.Success -> {

                    }
                    is Output.Failure ->
                        onErrorLE.postValue(errorMapper.toUiErrorMessage(result.error))
                }
            }
        }
    }

    override fun onInvalidUserName(errorResId: Int) {
        invalidUserNameMLD.value = errorResId
    }

    override fun onInvalidUserEmail(errorResId: Int) {
        invalidUserEmailMLD.value = errorResId
    }
}
