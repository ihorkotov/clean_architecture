package com.moto.tablet.ui.main.searchorders

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moto.tablet.data.GlobalRepository
import com.moto.tablet.data.UserRepository
import com.moto.tablet.model.Order
import com.moto.tablet.model.fakeOrders
import com.moto.tablet.util.SEARCH_INPUT_DELAY_MILLISECONDS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchOrdersViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val globalRepository: GlobalRepository
) : ViewModel() {

    val userType
        get() = userRepository.userType()

    var orders by mutableStateOf(listOf<Order>())

    var showProgress: Boolean by mutableStateOf(false)

    val query = MutableStateFlow("")
    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchKey: Flow<Unit> = query.debounce(SEARCH_INPUT_DELAY_MILLISECONDS)
        .distinctUntilChanged()
        .flatMapLatest { searchKey ->
            flow {
                search(searchKey)
                emptyFlow<Unit>()
            }
        }

    private fun search(searchKey: String) {
        viewModelScope.launch {
            showProgress = true
            orders = emptyList()
            delay(1500)
            orders = fakeOrders
            showProgress = false
        }
    }

    fun updateQuery(newQuery: String) {
        query.value = newQuery
    }

    fun updateSelectedCheckList(order: Order) {
        globalRepository.selectedCheckList = order
    }

    fun refreshOrders() {
        search(query.value)
    }

    fun logout() {
        userRepository.logout()
    }

    init {
        searchKey.launchIn(viewModelScope)
    }

}