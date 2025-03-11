package com.moto.tablet.data

import android.content.Context
import com.moto.tablet.R
import com.moto.tablet.model.LaborItem
import com.moto.tablet.model.OrderItem
import com.moto.tablet.model.PMSPackage
import com.moto.tablet.model.PartProduct
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepository @Inject constructor(
    @ApplicationContext context: Context
) {

    var orders = listOf(
        OrderItem(
            laborItem = LaborItem(
            1, 0f, context.resources.getString(R.string.labor), R.drawable.ic_labor
            ),
            count = 1
        )
    )

    val ordersCount: Int
        get() = maxOf(orders.size - 1, 0)

    val totalPrice: Float
        get() = orders.sumOf {
            it.price.toDouble() * it.count
        }.toFloat()

    val packages: List<PMSPackage>
        get() {
            return orders.mapNotNull {
                it.pmsPackage
            }
        }

    val partProducts: List<PartProduct>
        get() {
            return orders.mapNotNull {
                (it.partProduct)
            }
        }

    var customerNote = ""

    fun addPackage(pmsPackage: PMSPackage, count: Int) {
        val alreadyHave = orders.any { it.id == pmsPackage.orderId }
        orders = if (alreadyHave) {
            orders.map {
                if (it.id == pmsPackage.orderId) {
                    it.copy(count = it.count + count)
                } else {
                    it
                }
            }
        } else {
            orders + OrderItem(pmsPackage = pmsPackage, count = count)
        }
    }

    fun addPartProduct(partProduct: PartProduct, count: Int) {
        val alreadyHave = orders.any { it.id == partProduct.orderId }
        orders = if (alreadyHave) {
            orders.map {
                if (it.id == partProduct.orderId) {
                    it.copy(count = it.count + count)
                } else {
                    it
                }
            }
        } else {
            orders + OrderItem(partProduct = partProduct, count = count)
        }
    }

    fun increaseLaborItemCount(laborItem: LaborItem, count: Int) {
        val alreadyHave = orders.any { it.id == laborItem.orderId }
        orders = if (alreadyHave) {
            orders.map {
                if (it.id == laborItem.orderId) {
                    it.copy(count = it.count + count)
                } else {
                    it
                }
            }
        } else {
            orders + OrderItem(laborItem = laborItem, count = count)
        }
    }

    fun decreasePackageItemCount(pmsPackage: PMSPackage, count: Int = 1) {
        val alreadyHave = orders.any { it.id == pmsPackage.orderId }
        if (!alreadyHave) {
            return
        }
        orders = orders.map {
            if (it.id == pmsPackage.orderId) {
                it.copy(count = maxOf(it.count - count, 0))
            } else {
                it
            }
        }
    }

    fun decreasePartItemCount(partProduct: PartProduct, count: Int = 1) {
        val alreadyHave = orders.any { it.id == partProduct.orderId }
        if (!alreadyHave) {
            return
        }
        orders = orders.map {
            if (it.id == partProduct.orderId) {
                it.copy(count = maxOf(it.count - count, 0))
            } else {
                it
            }
        }
    }

    fun decreaseLaborItemCount(laborItem: LaborItem, count: Int = 1) {
        val alreadyHave = orders.any { it.id == laborItem.orderId }
        if (!alreadyHave) {
            return
        }
        orders = orders.map {
            if (it.id == laborItem.orderId) {
                it.copy(count = maxOf(it.count - count, 0))
            } else {
                it
            }
        }
    }

    fun updatePackageItemCount(pmsPackage: PMSPackage, count: Int) {
        val alreadyHave = orders.any { it.id == pmsPackage.orderId }
        if (!alreadyHave) {
            return
        }
        orders = orders.map {
            if (it.id == pmsPackage.orderId) {
                it.copy(count = count)
            } else {
                it
            }
        }
    }

    fun removePackage(pmsPackage: PMSPackage) {
        orders = orders.filter { it.id != pmsPackage.orderId}
    }

    fun removePartProduct(partProduct: PartProduct) {
        orders = orders.filter { it.id != partProduct.orderId }
    }

    fun removeLaborItem(laborItem: LaborItem) {
        orders = orders.filter { it.id != laborItem.orderId }
    }

    fun getPackageCount(pmsPackage: PMSPackage): Int {
        return orders.firstOrNull{
            it.id == pmsPackage.orderId
        }?.count ?: 0
    }

    fun getPartProductCount(partProduct: PartProduct): Int {
        return orders.firstOrNull{
            it.id == partProduct.orderId
        }?.count ?: 0
    }

    fun getLaborItemCount(laborItem: LaborItem): Int {
        return orders.firstOrNull{
            it.id == laborItem.orderId
        }?.count ?: 0
    }

    fun updateLaborItemPrice(newPrice: Float) {
        orders = orders.map {
            if (it.laborItem != null) {
                it.copy(
                    laborItem = it.laborItem.copy(price = newPrice)
                )
            } else {
                it
            }
        }
    }

    fun getCurrentLaborPrice(): Float {
        return orders.firstOrNull { it.laborItem != null }?.laborItem?.price ?: 0f
    }

    fun updatePackage(pmsPackage: PMSPackage) {
        orders = orders.map {
            if (it.pmsPackage?.id == pmsPackage.id) {
                it.copy(
                    pmsPackage = pmsPackage
                )
            } else {
                it
            }
        }
    }
}