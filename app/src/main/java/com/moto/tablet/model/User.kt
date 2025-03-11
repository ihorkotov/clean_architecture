package com.moto.tablet.model

enum class UserType {
    Mechanic,
    ServiceAdvisor
}

data class User(
    val id: Long,
    val name: String,
    val userName: String,
    val emailAddress: String?,
    val type: UserType,
    val mechanicCode: String
)

val fakeMechanicUser = User(
    id = 1,
    name = "Mechanic User",
    userName = "mechanic_user",
    emailAddress = "",
    type = UserType.Mechanic,
    mechanicCode = "123456"
)

val fakeServiceAdvisorUser = User(
    id = 2,
    name = "Service Advisor User",
    userName = "service_advisor_user",
    emailAddress = "",
    type = UserType.ServiceAdvisor,
    mechanicCode = "123456"
)