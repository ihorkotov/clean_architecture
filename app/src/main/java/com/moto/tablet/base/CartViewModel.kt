package com.moto.tablet.base

import androidx.lifecycle.ViewModel
import com.moto.tablet.data.CartRepository
import com.moto.tablet.model.LaborItem
import com.moto.tablet.model.OrderItem
import com.moto.tablet.model.PMSPackage
import com.moto.tablet.model.PartProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map


open class CartViewModel(
    private val cartRepository: CartRepository
) : ViewModel() {

    val orders: MutableStateFlow<List<OrderItem>> =
        MutableStateFlow(cartRepository.orders)

//    val orders: StateFlow<List<OrderItem>> get() = orders

    val cartTotalCount: Flow<Int> = orders.map { it.size }

    val cartTotalPrice: Flow<Float>
        get() = orders.map { orders ->
        orders.sumOf {
            it.price.toDouble() * it.count
        }.toFloat()
    }

    val cartTotalCountWithoutLabor: Flow<Int> = orders.map {
        it.filter { order ->
            order.laborItem == null
        }.size
    }

    val cartTotalPriceWithoutLabor: Flow<Float> get() = orders.map { orders ->
        orders.filter { order ->
            order.laborItem == null
        }.sumOf {
            it.price.toDouble() * it.count
        }.toFloat()
    }

    fun addPackageToCart(pmsPackage: PMSPackage, count: Int) {
        cartRepository.addPackage(pmsPackage, count)
        orders.value = cartRepository.orders
    }

    fun addPartProductToCart(partProduct: PartProduct, count: Int) {
        cartRepository.addPartProduct(partProduct, count)
        orders.value = cartRepository.orders
    }

    fun increaseLaborItemCount(laborItem: LaborItem, count: Int) {
        cartRepository.increaseLaborItemCount(laborItem, count)
        orders.value = cartRepository.orders
    }

    fun decreasePackageItemCount(pmsPackage: PMSPackage, count: Int) {
        cartRepository.decreasePackageItemCount(pmsPackage, count)
        orders.value = cartRepository.orders
    }

    fun decreasePartProductItemCount(partProduct: PartProduct, count: Int) {
        cartRepository.decreasePartItemCount(partProduct, count)
        orders.value = cartRepository.orders
    }

    fun decreaseLaborItemCount(laborItem: LaborItem, count: Int) {
        cartRepository.decreaseLaborItemCount(laborItem, count)
        orders.value = cartRepository.orders
    }

    fun removePackage(pmsPackage: PMSPackage) {
        cartRepository.removePackage(pmsPackage)
        orders.value = cartRepository.orders
    }

    fun removePartProduct(partProduct: PartProduct) {
        cartRepository.removePartProduct(partProduct)
        orders.value = cartRepository.orders
    }

    fun removeLaborItem(laborItem: LaborItem) {
        cartRepository.removeLaborItem(laborItem)
        orders.value = cartRepository.orders
    }

    fun updatePackage(pmsPackage: PMSPackage) {
        cartRepository.updatePackage(pmsPackage)
    }

    fun getPackageCount(pmsPackage: PMSPackage): Int {
        return cartRepository.getPackageCount(pmsPackage)
    }

    fun updatePackageCount(pmsPackage: PMSPackage, count: Int) {
        cartRepository.updatePackageItemCount(pmsPackage, count)
    }
}