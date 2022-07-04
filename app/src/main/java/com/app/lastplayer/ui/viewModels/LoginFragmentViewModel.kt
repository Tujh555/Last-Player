package com.app.lastplayer.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.lastplayer.database.entities.UserEntity
import com.app.lastplayer.usecases.database.InsertUserUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginFragmentViewModel @Inject constructor(
    private val insertUserUseCase: InsertUserUseCase
) : ViewModel() {
    fun insertUser(userId: String) {
        val userEntity = UserEntity(userId)

        viewModelScope.launch {
            insertUserUseCase(userEntity)
        }
    }
}