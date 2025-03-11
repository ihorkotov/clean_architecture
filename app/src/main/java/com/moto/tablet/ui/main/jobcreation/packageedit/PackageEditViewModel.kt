package com.moto.tablet.ui.main.jobcreation.packageedit

import com.moto.tablet.base.CartViewModel
import com.moto.tablet.data.CartRepository
import com.moto.tablet.data.GlobalRepository
import com.moto.tablet.model.PMSPackage
import com.moto.tablet.model.PMSPackageContent
import com.moto.tablet.model.fakePackage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class PackageEditViewModel @Inject constructor(
    cartRepository: CartRepository,
    globalRepository: GlobalRepository
) : CartViewModel(cartRepository) {

    val selectedPackage: MutableStateFlow<PMSPackage> =
        MutableStateFlow(globalRepository.selectedEditPMSPackage ?: fakePackage())

    fun deleteContentFromPackage(pmsPackage: PMSPackage, content: PMSPackageContent) {
        val newPackage = pmsPackage.copy(
            contentList = pmsPackage.contentList.map {
                if (it.id == content.id) {
                    it.copy(isRemoved = true)
                } else {
                    it
                }
            }
        )
        updatePackage(newPackage)
        selectedPackage.value = newPackage
    }

}