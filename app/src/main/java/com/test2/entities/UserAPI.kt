package com.test2.entities

import java.math.BigDecimal

data class UserAPI(
    val code: Int,
    val id: BigDecimal,
    val name: String,
    val age: BigDecimal
)
