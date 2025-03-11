package com.moto.tablet.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.moto.tablet.R
import com.moto.tablet.data.UserRepository
import com.moto.tablet.model.fakeMechanicUser
import com.moto.tablet.model.fakeServiceAdvisorUser
import com.moto.tablet.util.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var userRepository: UserRepository

    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var goToNextPage by mutableStateOf(false)

    fun updateUsername(input: String) {
        username = input
    }

    fun updatePassword(input: String) {
        password = input
    }

    fun updateEmail(input: String) {
        email = input
    }

    fun sendEmailForgetPassword() {

    }

    fun login() {
        if (username.isEmpty()) {
            SnackbarManager.showErrorMessage(R.string.enter_user_name)
            return
        }
        if (password.isEmpty()) {
            SnackbarManager.showErrorMessage(R.string.enter_password)
            return
        }
        val user = if (username == fakeMechanicUser.userName) {
            fakeMechanicUser
        } else {
            fakeServiceAdvisorUser
        }
        userRepository.updateUser(user)
        goToNextPage = true
    }
}