package com.test2.repositories

import com.test2.entities.UserAPI
import java.math.BigDecimal

class UserService {
    fun getUser(): UserAPI = UserAPI(id = BigDecimal(1), name = "anton", age = BigDecimal(1), code = 400)
}