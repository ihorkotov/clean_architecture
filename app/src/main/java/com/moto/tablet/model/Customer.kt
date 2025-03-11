package com.moto.tablet.model

class Customer(
    val id: Long,
    val fullName: String?,
    val emailAddress: String?,
    val contactNumber: String?,
    val birthday: String?,
    val completeAddress: String?
)

val fakeCustomer = Customer(
    id = 1,
    fullName = "Juan Dela Cruz",
    emailAddress = "juandelacruz@gmail.com",
    contactNumber = "091712345678",
    birthday = "Nov 5, 1950",
    completeAddress = "#12345 Walang Sawang Maghintay St. Barangay Mapagmahal Quezon City"
)

val fakeCustomer2 = Customer(
    id = 2,
    fullName = "Marcel Ritter",
    emailAddress = "juandelacruz@gmail.com",
    contactNumber = "091712345678",
    birthday = "Nov 5, 1950",
    completeAddress = "#12345 Walang Sawang Maghintay St. Barangay Mapagmahal Quezon City"
)

val fakeCustomer3 = Customer(
    id = 3,
    fullName = "Yongliang Zhang",
    emailAddress = "juandelacruz@gmail.com",
    contactNumber = "091712345678",
    birthday = "Nov 5, 1950",
    completeAddress = "#12345 Walang Sawang Maghintay St. Barangay Mapagmahal Quezon City"
)

val fakeCustomer4 = Customer(
    id = 4,
    fullName = "Jucied Dela Cruz",
    emailAddress = "juandelacruz@gmail.com",
    contactNumber = "091712345678",
    birthday = "Nov 5, 1950",
    completeAddress = "#12345 Walang Sawang Maghintay St. Barangay Mapagmahal Quezon City"
)

val fakeCustomer5 = Customer(
    id = 5,
    fullName = "Young",
    emailAddress = "juandelacruz@gmail.com",
    contactNumber = "091712345678",
    birthday = "Nov 5, 1950",
    completeAddress = "#12345 Walang Sawang Maghintay St. Barangay Mapagmahal Quezon City"
)

val fakeCustomers = listOf(
    fakeCustomer,
    fakeCustomer2,
    fakeCustomer3,
    fakeCustomer4,
    fakeCustomer5,
)