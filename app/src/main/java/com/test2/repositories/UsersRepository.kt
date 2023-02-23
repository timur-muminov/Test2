package com.test2.repositories

import com.test2.entities.User

interface UsersRepository {
    fun getUser(): User
}