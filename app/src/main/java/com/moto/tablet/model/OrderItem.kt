package com.moto.tablet.model

import androidx.annotation.DrawableRes

data class OrderItem(
    val pmsPackage: PMSPackage? = null,
    val partProduct: PartProduct? = null,
    val laborItem: LaborItem? = null,
    val count: Int = 0
) {
    val id: String
        get() = when {
            pmsPackage != null -> pmsPackage.orderId
            partProduct != null -> partProduct.orderId
            laborItem != null -> laborItem.orderId
            else -> "null"
        }

    val price: Float
        get() = when {
            pmsPackage != null -> pmsPackage.price
            partProduct != null -> partProduct.price
            laborItem != null -> laborItem.price
            else -> 0f
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OrderItem) return false

        if (pmsPackage != other.pmsPackage) return false
        if (partProduct != other.partProduct) return false
        if (laborItem != other.laborItem) return false
        return count == other.count
    }

    override fun hashCode(): Int {
        var result = pmsPackage?.hashCode() ?: 0
        result = 31 * result + (partProduct?.hashCode() ?: 0)
        result = 31 * result + (laborItem?.hashCode() ?: 0)
        result = 31 * result + count
        return result
    }


}

data class LaborItem(
    val id: Long,
    val price: Float,
    val title: String,
    @DrawableRes val image: Int
) {
    val orderId: String
        get() = "labor_$id"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LaborItem) return false

        if (id != other.id) return false
        if (price != other.price) return false
        if (title != other.title) return false
        return image == other.image
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + image
        return result
    }


}