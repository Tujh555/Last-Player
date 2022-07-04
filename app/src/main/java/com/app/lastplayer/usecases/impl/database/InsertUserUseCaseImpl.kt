package com.app.lastplayer.usecases.impl.database

import com.app.lastplayer.database.entities.UserEntity
import com.app.lastplayer.repositories.DatabaseRepository
import com.app.lastplayer.usecases.database.InsertUserUseCase
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InsertUserUseCaseImpl @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : InsertUserUseCase {
    override fun invoke(user: UserEntity) {
        flow<Unit> {
            databaseRepository.insertUser(user)
        }
    }
}