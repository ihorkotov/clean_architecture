package com.moto.tablet.ui.main.landing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moto.tablet.data.GlobalRepository
import com.moto.tablet.data.UserRepository
import com.moto.tablet.model.Order
import com.moto.tablet.model.fakeOrders
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val globalRepository: GlobalRepository,
) : ViewModel() {
    val userType
        get() = userRepository.userType()

    var showProgress by mutableStateOf(false)

    var orders by mutableStateOf(listOf<Order>())

    fun updateSelectedCheckList(order: Order) {
        globalRepository.selectedCheckList = order
    }

    fun removeCheckList(order: Order?) {
        orders = orders.filter { it.id != order?.id }
    }

    fun refreshOrders() {
        viewModelScope.launch {
            showProgress = true
            orders = emptyList()
            delay(1500)
            orders = fakeOrders
            showProgress = false
        }
    }

    init {
        refreshOrders()
    }
}