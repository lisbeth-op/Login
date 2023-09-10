package com.example.myapplication.Login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Authentication

import com.example.myapplication.util.stateIn
import com.example.myapplication.util.MutableSavedState
import com.example.myapplication.util.ProgressLoader
import com.example.myapplication.util.SampleLoginDispatchers
import com.example.myapplication.util.combineFlows
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(dispatchers: SampleLoginDispatchers, stateHandle: SavedStateHandle) : ViewModel(){
    private val username = MutableSavedState(
        stateHandle,
        "UserName",
        defValue = ""
    )

    private val password = MutableSavedState(
        stateHandle,
        "password",
        defValue = ""
    )

    private val passwordVisibility = MutableSavedState(
        stateHandle,
        "password_key",
        defValue = false
    )

    private val loadingProgress = ProgressLoader()

    val state = combineFlows(
        username.flow,
        password.flow,
        passwordVisibility.flow,
        loadingProgress.flow
    ) { username, password, passwordToggle, isLoading ->
        Authentication(
            nombreDeUsuario = username,
            clave = password,
            visiblidadDeClave  = passwordToggle,
            loading = isLoading
        )
    }.stateIn(
        coroutineScope = viewModelScope + dispatchers.main,
        initialValue = Authentication.EMPTY_STATE
    )

    fun userNameChanged(userName: String) {
        username.value = userName
    }

    fun passwordChanged(updatedPassword: String) {
        password.value = updatedPassword
    }

    fun passwordVisibility(visibility: Boolean) {
        passwordVisibility.value = visibility
    }

    fun login() {
        loadingProgress.start()
    }
}