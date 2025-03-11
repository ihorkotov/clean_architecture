package com.moto.tablet.ui.main.jobcreation.checkout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.moto.tablet.base.CartViewModel
import com.moto.tablet.data.CartRepository
import com.moto.tablet.data.GlobalRepository
import com.moto.tablet.model.LaborItem
import com.moto.tablet.model.PMSPackage
import com.moto.tablet.model.PartProduct
import com.moto.tablet.util.LABOR_PRICE_INPUT_DELAY_MILLISECONDS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val globalRepository: GlobalRepository,
) : CartViewModel(cartRepository) {

    val cartPmsPackages: Flow<List<PMSPackage>> = orders.map { orders ->
        orders.mapNotNull { it.pmsPackage }
    }

    val cartPartProducts: Flow<List<PartProduct>> = orders.map { orders ->
        orders.mapNotNull { it.partProduct }
    }

    val laborItems: Flow<List<LaborItem>> = orders.map { orders ->
        orders.mapNotNull { it.laborItem }
    }

    val laborPriceString = MutableStateFlow(cartRepository.getCurrentLaborPrice().toString())
    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val laborInputPrice: Flow<Float> = laborPriceString.debounce(LABOR_PRICE_INPUT_DELAY_MILLISECONDS)
        .distinctUntilChanged()
        .flatMapLatest { price ->
            flow {
                val value = price.toFloatOrNull() ?: 0f
                updateLaborItemPrice(value)
                emptyFlow<Float>()
            }
        }

    var customerNote by mutableStateOf(cartRepository.customerNote)

    var jobCreatedNumber: String by  mutableStateOf("")

    fun onLaborItemPriceChanged(priceString: String) {
        laborPriceString.value = priceString
    }

    private fun updateLaborItemPrice(updatedPrice: Float) {
        cartRepository.updateLaborItemPrice(updatedPrice)
        orders.value = cartRepository.orders
    }

    fun packageCount(pmsPackage: PMSPackage): Int {
        return cartRepository.getPackageCount(pmsPackage)
    }

    fun partProductCount(partProduct: PartProduct): Int {
        return cartRepository.getPartProductCount(partProduct)
    }

    fun laborItemCount(laborItem: LaborItem): Int {
        return cartRepository.getLaborItemCount(laborItem)
    }

    fun increaseItemCount(item: Any?, count: Int = 1) {
        when (item) {
            is PMSPackage -> addPackageToCart(item, count)
            is PartProduct -> addPartProductToCart(item, count)
            is LaborItem -> increaseLaborItemCount(item, count)
        }
    }
    fun decreaseItemCount(item: Any?, count: Int = 1) {
        when (item) {
            is PMSPackage -> decreasePackageItemCount(item, count)
            is PartProduct -> decreasePartProductItemCount(item, count)
            is LaborItem -> decreaseLaborItemCount(item, count)
        }
    }
    fun removeItem(item: Any?) {
        when (item) {
            is PMSPackage -> removePackage(item)
            is PartProduct -> removePartProduct(item)
            is LaborItem -> removeLaborItem(item)
        }
    }

    fun refreshOrders() {
        orders.value = cartRepository.orders
    }

    fun updateCustomerNote(note: String?) {
        customerNote = note ?: ""
        cartRepository.customerNote = note ?: ""
    }

    fun selectPackageEdit(pmsPackage: PMSPackage) {
        globalRepository.selectedEditPMSPackage = pmsPackage
    }

    fun placeOrder() {
        jobCreatedNumber = "A10000350"
    }
}