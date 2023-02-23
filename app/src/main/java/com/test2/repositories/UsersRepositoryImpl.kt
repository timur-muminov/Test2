package com.test2.repositories

import com.test2.entities.User

class UsersRepositoryImpl(private val service: UserService, private val mapper: Mapper) : UsersRepository {
    override fun getUser(): User {
        return try {
            val result = service.getUser()
            if (result.code == 400) throw NumberFormatException()
            else mapper.mapUser(result)
        } catch (e: java.lang.NumberFormatException) {
            throw e
            //mapper.mapUser(service.getUser().copy(code = 400))
        }
    }
}

