package com.moto.tablet.ui.main.jobcreation.packagedetail

import com.moto.tablet.base.CartViewModel
import com.moto.tablet.data.CartRepository
import com.moto.tablet.data.GlobalRepository
import com.moto.tablet.model.PMSPackage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PackageDetailViewModel @Inject constructor(
    cartRepository: CartRepository,
    private val globalRepository: GlobalRepository
) : CartViewModel(cartRepository) {
    fun selectedPMSPackage(): PMSPackage? {
        return globalRepository.selectedPMSPackage
    }
}