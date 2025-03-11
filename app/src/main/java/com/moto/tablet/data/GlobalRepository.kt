package com.moto.tablet.data

import com.moto.tablet.model.Order
import com.moto.tablet.model.PMSPackage
import com.moto.tablet.model.PartProduct
import com.moto.tablet.ui.main.Screen
import com.moto.tablet.ui.main.jobcreation.searchresult.SearchKey
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalRepository @Inject constructor() {
    var selectedPMSPackage: PMSPackage? = null
    var selectedEditPMSPackage: PMSPackage? = null
    var searchKey: SearchKey? = null
    var fullScreenImages: List<String> = listOf()
    var productForDetail: PartProduct? = null
    var selectedCheckList: Order? = null
    var currentSelectedTab: Screen = Screen.Landing
}