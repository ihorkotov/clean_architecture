package com.moto.tablet.util

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Currency
import java.util.Date
import java.util.Locale

fun currencyString(amount: Float): String {
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 2
    format.currency = Currency.getInstance("USD")

    return format.format(amount).replace("$", "P")
}

fun Long.timeString(format: String = DEFAULT_TIME_FORMAT): String {
    val timeInstance = SimpleDateFormat(format, Locale.getDefault())
    return timeInstance.format(Date(this))
}

fun Long.dateString(format: String = DEFAULT_NUMERIC_DATE_FORMAT): String {
    val timeInstance = SimpleDateFormat(format, Locale.getDefault())
    return timeInstance.format(Date(this))
}