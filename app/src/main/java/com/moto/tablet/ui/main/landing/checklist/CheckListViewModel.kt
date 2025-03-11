package com.moto.tablet.ui.main.landing.checklist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moto.tablet.data.GlobalRepository
import com.moto.tablet.data.UserRepository
import com.moto.tablet.model.Order
import com.moto.tablet.model.PMSPackage
import com.moto.tablet.model.PartProduct
import com.moto.tablet.model.UserType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckListViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val globalRepository: GlobalRepository,
) : ViewModel() {
    var order: Order? = null
    val userType: UserType
        get() = userRepository.userType()
    val mechanicCode: String
        get() = userRepository.mechanicCode

    var showProgress: Boolean by mutableStateOf(false)
    var updatedOrderStatus: Boolean by mutableStateOf(false)

    fun updateSelectedPMSPackage(pmsPackage: PMSPackage) {
        globalRepository.selectedPMSPackage = pmsPackage
    }

    fun updateSelectedPartProduct(partProduct: PartProduct) {
        globalRepository.productForDetail = partProduct
    }

    fun startOrder() {
        viewModelScope.launch {
            showProgress = true
            delay(500)
            showProgress = false
            updatedOrderStatus = true
        }
    }

    init {
        order = globalRepository.selectedCheckList
    }
}