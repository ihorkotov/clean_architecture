package com.moto.tablet.data

import com.moto.tablet.model.User
import com.moto.tablet.model.UserType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor() {
    private var user: User? = null

    val mechanicCode: String
        get() = user?.mechanicCode ?: ""

    fun updateUser(user: User) {
        this.user = user
    }

    fun userType(): UserType {
        return user?.type ?: UserType.Mechanic
    }

    fun logout() {
        user = null
    }

}