package com.test2.repositories

import com.test2.entities.User
import com.test2.entities.UserAPI
import java.math.BigDecimal

class Mapper {

    fun mapUser(userAPI: UserAPI): User = User(id = userAPI.id, name = userAPI.name, age = BigDecimal(2))
}