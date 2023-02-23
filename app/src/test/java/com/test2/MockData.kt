package com.test2

import com.test2.entities.User
import com.test2.entities.UserAPI
import java.math.BigDecimal

internal object MockData {

    val SuccessUserAPI = UserAPI(id = BigDecimal(1), name = "anton", age = BigDecimal(1), code = 200)
    val ErrorUserAPI = UserAPI(id = BigDecimal(1), name = "anton", age = BigDecimal(1), code = 400)
    val user = User(id = BigDecimal(1), name = "anton", age = BigDecimal(2))
}