package com.example.adminhostel_locator.model

data class UserModel(
    val name: String? = null,
    val nameOfProperty: String? = null,
    val email: String? = null,
    val password: String? = null,
    var address:String? = null,
    var phone: String? = null,
)
