package com.moto.tablet.model

import androidx.compose.ui.graphics.Color
import com.moto.tablet.R
import com.moto.tablet.ui.theme.Blue
import com.moto.tablet.ui.theme.Green
import com.moto.tablet.ui.theme.Orange
import com.moto.tablet.util.timeString

enum class OrderStatus {
    NextToService,
    Ongoing,
    Done,
}

data class Order(
    val id: Long,
    val number: String,
    val createdAt: Long,
    val status: OrderStatus,
    val start: Long?,
    val end: Long?,
    val customer: Customer,
    val packages: List<PMSPackage>?,
    val partProduct: List<PartProduct>?,
    val customerNote: String?,
    val photoUrl: List<String>?,
    val sign: String?,
) {
    val statusStringResource: Int
        get() = when(status) {
            OrderStatus.NextToService -> R.string.next_to_service
            OrderStatus.Ongoing -> R.string.ongoing
            OrderStatus.Done -> R.string.done
        }
    val statusStringColor: Color
        get() = when(status) {
            OrderStatus.NextToService -> Orange
            OrderStatus.Ongoing -> Green
            OrderStatus.Done -> Blue
        }
    val startEndTimeRangeString: String
        get() {
            if (start == null || end == null) {
                return ""
            }
            return "${start.timeString()} - ${end.timeString()}"
        }
}

val fakeOrder = Order(
    id = 1,
    number = "ABC 12345",
    createdAt = 1708436334318,
    status = OrderStatus.NextToService,
    start = null,
    end = null,
    customer = fakeCustomer,
    packages = fakePackages(),
    partProduct = fakePartProducts(),
    customerNote = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque a risus vel nisl vulputate gravida ut eu justo. Vestibulum ut risus ut tortor volutpat venenatis auctor in quam. Morbi eu bibendum justo. Phasellus ac purus ex. Proin sit amet velit eu lorem vestibulum malesuada rhoncus a leo. Cras volutpat tempor suscipit. In a neque consectetur, tristique metus sit amet, placerat odio.",
    photoUrl = emptyList(),
    sign = null
)

val fakeOrder2 = Order(
    id = 2,
    number = "DEF 12345",
    createdAt = 1708436334318,
    status = OrderStatus.Ongoing,
    start = 1708436334318,
    end = null,
    customer = fakeCustomer,
    packages = fakePackages(),
    partProduct = fakePartProducts(),
    customerNote = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque a risus vel nisl vulputate gravida ut eu justo. Vestibulum ut risus ut tortor volutpat venenatis auctor in quam. Morbi eu bibendum justo. Phasellus ac purus ex. Proin sit amet velit eu lorem vestibulum malesuada rhoncus a leo. Cras volutpat tempor suscipit. In a neque consectetur, tristique metus sit amet, placerat odio.",
    photoUrl = emptyList(),
    sign = null
)

val fakeOrder3 = Order(
    id = 3,
    number = "GHI 12345",
    createdAt = 1708436334318,
    status = OrderStatus.Done,
    start = 1708436334318,
    end = 1708436898989,
    customer = fakeCustomer,
    packages = fakePackages(),
    partProduct = fakePartProducts(),
    customerNote = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque a risus vel nisl vulputate gravida ut eu justo. Vestibulum ut risus ut tortor volutpat venenatis auctor in quam. Morbi eu bibendum justo. Phasellus ac purus ex. Proin sit amet velit eu lorem vestibulum malesuada rhoncus a leo. Cras volutpat tempor suscipit. In a neque consectetur, tristique metus sit amet, placerat odio.",
    photoUrl = emptyList(),
    sign = null
)

val fakeOrders = listOf(
    fakeOrder,
    fakeOrder.copy(id = 10),
    fakeOrder2,
    fakeOrder2.copy(id = 20),
    fakeOrder3,
    fakeOrder3.copy(id = 30),
)