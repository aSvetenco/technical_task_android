package com.sa.gorestuserstask.presentation.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sa.gorestuserstask.R
import com.sa.gorestuserstask.domain.entity.User
import com.sa.gorestuserstask.domain.usecase.DeleteUserUseCase
import com.sa.gorestuserstask.domain.usecase.GetUsersFromLastPageUseCase
import com.sa.gorestuserstask.domain.Output
import com.sa.gorestuserstask.presentation.utils.DomainErrorMapper
import com.sa.gorestuserstask.presentation.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserListViewModel @Inject constructor(
    private val getUserListUseCase: GetUsersFromLastPageUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val errorMapper: DomainErrorMapper
) : ViewModel() {

    private val onUserListLoadedMLD = MutableLiveData<List<User>>()
    val onUserListLoaded: LiveData<List<User>> = onUserListLoadedMLD

    private val isLoadingMLD = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = isLoadingMLD

    val onErrorLE = SingleLiveEvent<String>()
    val onSuccessLE = SingleLiveEvent<Int>()

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        isLoadingMLD.value = true
        viewModelScope.launch {
            val result = getUserListUseCase.invoke(Unit)
            isLoadingMLD.postValue(false)
            when (result) {
                is Output.Success -> onUserListLoadedMLD.postValue(result.data)
                is Output.Failure ->
                    onErrorLE.postValue(errorMapper.toUiErrorMessage(result.error))
            }
        }
    }

    fun deleteUser(id: Int) {
        isLoadingMLD.value = true
        viewModelScope.launch {
            val result = deleteUserUseCase.invoke(id)
            isLoadingMLD.postValue(false)
            when (result) {
                is Output.Success -> {
                    onUserListLoadedMLD.postValue(result.data)
                    onSuccessLE.postValue(R.string.delete_user_successfully_message)
                }
                is Output.Failure ->
                    onErrorLE.postValue(errorMapper.toUiErrorMessage(result.error))
            }
        }
    }
}
