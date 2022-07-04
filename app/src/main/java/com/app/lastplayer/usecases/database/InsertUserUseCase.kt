package com.app.lastplayer.usecases.database

import com.app.lastplayer.database.entities.UserEntity

interface InsertUserUseCase {
    suspend operator fun invoke(user: UserEntity)
}