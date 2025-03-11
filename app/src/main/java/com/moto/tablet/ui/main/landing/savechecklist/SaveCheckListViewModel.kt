package com.moto.tablet.ui.main.landing.savechecklist

import androidx.lifecycle.ViewModel
import com.moto.tablet.data.GlobalRepository
import com.moto.tablet.data.UserRepository
import com.moto.tablet.model.Order
import com.moto.tablet.ui.main.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SaveCheckListViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val globalRepository: GlobalRepository,
) : ViewModel() {
    var order: Order? = null

    val isSearchTab: Boolean
        get() = globalRepository.currentSelectedTab == Screen.SearchOrders

    init {
        order = globalRepository.selectedCheckList
    }
}