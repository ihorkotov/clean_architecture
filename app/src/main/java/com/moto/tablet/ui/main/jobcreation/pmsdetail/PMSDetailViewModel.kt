package com.moto.tablet.ui.main.jobcreation.pmsdetail

import com.moto.tablet.base.CartViewModel
import com.moto.tablet.data.CartRepository
import com.moto.tablet.data.GlobalRepository
import com.moto.tablet.model.PMS
import com.moto.tablet.model.PMSPackage
import com.moto.tablet.model.fakePackages
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PMSDetailViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val globalRepository: GlobalRepository
) : CartViewModel(cartRepository) {

    fun getPackagesWithType(type: PMS): List<PMSPackage> {
        val list = fakePackages()
        return when(type) {
            PMS.Scooter -> list + list + list + list + list
            PMS.BigBikes -> list + list.first()
            PMS.Underbone -> list + list
        }
    }

    fun savePMSPackageForDetailPageNavigate(pmsPackage: PMSPackage) {
        globalRepository.selectedPMSPackage = pmsPackage
    }

    fun refreshOrders() {
        orders.value = cartRepository.orders
    }
}
