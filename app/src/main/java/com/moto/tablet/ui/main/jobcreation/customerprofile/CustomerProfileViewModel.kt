package com.moto.tablet.ui.main.jobcreation.customerprofile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moto.tablet.R
import com.moto.tablet.model.Customer
import com.moto.tablet.model.fakeCustomers
import com.moto.tablet.util.SEARCH_INPUT_DELAY_MILLISECONDS
import com.moto.tablet.util.SnackbarManager
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
import kotlinx.coroutines.flow.map
import javax.inject.Inject

enum class CustomerInfoType {
    Name,
    Birthdate,
    EmailAddress,
    ContactNumber,
    CompleteAddress,
}

@HiltViewModel
class CustomerProfileViewModel @Inject constructor() : ViewModel() {

    private var customer: Customer? = null

    var name: String by mutableStateOf("")
        private set

    var birthdate: String by mutableStateOf("")
        private set

    var email: String by mutableStateOf("")
        private set

    var contactNumber: String by mutableStateOf("")
        private set

    var completeAddress: String by mutableStateOf("")
        private set

    var termsAndConditionsChecked: Boolean by mutableStateOf(false)
        private set

    var privacyPolicyChecked: Boolean by mutableStateOf(false)
        private set

    var receiveMarketingChecked: Boolean by mutableStateOf(false)
        private set

    var goToNextPage: Boolean by  mutableStateOf(false)

    private val searchedCustomerList = MutableStateFlow<List<Customer>>(listOf())
    val searchCustomerNames = searchedCustomerList.map { customers ->
        customers.mapNotNull { it.fullName }
    }
    private val inputUserNameString = MutableStateFlow("")
    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private val userNameSearchKey: Flow<String> = inputUserNameString.debounce(SEARCH_INPUT_DELAY_MILLISECONDS)
        .distinctUntilChanged()
        .flatMapLatest { searchKey ->
            flow {
                searchCustomer(searchKey)
                emptyFlow<String>()
            }
        }

    fun updateInfo(type: CustomerInfoType, updated: String) {
        when(type) {
            CustomerInfoType.Name -> {
                name = updated
                inputUserNameString.value = updated
            }
            CustomerInfoType.Birthdate -> birthdate = updated
            CustomerInfoType.EmailAddress -> email = updated
            CustomerInfoType.ContactNumber -> contactNumber = updated
            CustomerInfoType.CompleteAddress -> completeAddress = updated
        }
    }

    fun updateSelection(type: CustomerInfoType, selected: String) {
        if (type == CustomerInfoType.Name) {
            customer = searchedCustomerList.value.firstOrNull {
                it.fullName == selected
            }
            searchedCustomerList.value = listOf()
            name = customer?.fullName ?: ""
            birthdate = customer?.birthday ?: ""
            email = customer?.emailAddress ?: ""
            contactNumber = customer?.contactNumber ?: ""
            completeAddress = customer?.completeAddress ?: ""
        }
    }

    fun termsAndConditionsCheckedChanged(isChecked: Boolean) {
        termsAndConditionsChecked = isChecked
    }

    fun privacyPolicyCheckedChanged(isChecked: Boolean) {
        privacyPolicyChecked = isChecked
    }

    fun receiveMarketingCheckedChanged(isChecked: Boolean) {
        receiveMarketingChecked = isChecked
    }

    private suspend fun searchCustomer(searchKey: String) {
        if (searchKey.isEmpty()) {
            searchedCustomerList.value = listOf()
            return
        }
        if (customer != null && searchKey == customer?.fullName) {
            return
        }
        delay(500)
        searchedCustomerList.value = fakeCustomers.filter {
            it.fullName?.startsWith(searchKey) == true
        }
    }

    fun onClickSave() {
        if (name.isEmpty()) {
            SnackbarManager.showErrorMessage(R.string.enter_first_name_last_name)
            return
        }
        if (birthdate.isEmpty()) {
            SnackbarManager.showErrorMessage(R.string.enter_birthdate)
            return
        }
        if (email.isEmpty()) {
            SnackbarManager.showErrorMessage(R.string.enter_email)
            return
        }
        if (contactNumber.isEmpty()) {
            SnackbarManager.showErrorMessage(R.string.enter_contact_number)
            return
        }
        if (completeAddress.isEmpty()) {
            SnackbarManager.showErrorMessage(R.string.enter_complete_address)
            return
        }
        customer = Customer(
            id = 0,
            fullName = name,
            birthday = birthdate,
            emailAddress = email,
            contactNumber = contactNumber,
            completeAddress = completeAddress
        )
        goToNextPage = true
    }

    init {
        userNameSearchKey.launchIn(viewModelScope)
    }
}

