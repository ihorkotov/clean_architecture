package com.moto.tablet.ui.main.jobcreation.searchproduct

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.moto.tablet.R
import com.moto.tablet.data.CartRepository
import com.moto.tablet.data.GlobalRepository
import com.moto.tablet.ui.main.jobcreation.searchresult.BrandQuery
import com.moto.tablet.ui.main.jobcreation.searchresult.SearchKey
import com.moto.tablet.util.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchProductViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val globalRepository: GlobalRepository
) : ViewModel() {
    val cartItemCount: Int
        get() = cartRepository.ordersCount

    var query: String by mutableStateOf("")
        private set

    var selectedBrand: String by mutableStateOf("")
        private set

    var selectedModel: String by mutableStateOf("")
        private set

    var selectedYear: String by mutableStateOf("")
        private set

    var brandList: List<String> by mutableStateOf(listOf())
        private set

    var modelList: List<String> by mutableStateOf(listOf())
        private set

    var yearList: List<String> by mutableStateOf(listOf())
        private set

    var goToNextPage: Boolean by  mutableStateOf(false)

    fun updateQuery(newQuery: String) {
        query = newQuery
    }

    private fun updateSelectedBrand(new: String) {
        selectedBrand = new
        modelList = createModelList(new)
        updateSelectedModel(modelList.firstOrNull() ?: "")
    }

    private fun updateSelectedModel(new: String) {
        selectedModel = new
        yearList = createYearList(new)
        updateSelectedYear(yearList.firstOrNull() ?: "")
    }

    private fun updateSelectedYear(new: String) {
        selectedYear = new
    }

    fun startSearch() {
        val searchKey = when {
            query.isNotEmpty() -> {
                SearchKey(
                    query = query,
                    brandQuery = null
                )
            }
            selectedBrand.isNotEmpty() -> {
                SearchKey(
                    query = null,
                    brandQuery = BrandQuery(
                        brand = selectedBrand,
                        model = selectedModel,
                        year = selectedYear
                    )
                )
            }
            else -> {
                null
            }
        }
        if (searchKey == null) {
            SnackbarManager.showErrorMessage(R.string.search_key_error_message)
        } else {
            globalRepository.searchKey = searchKey
            goToNextPage = true
        }
    }

    fun updateSelection(type: DropDownType, new: String) {
        when(type) {
            DropDownType.Brand -> updateSelectedBrand(new)
            DropDownType.Model -> updateSelectedModel(new)
            DropDownType.Year -> updateSelectedYear(new)
        }
    }

    private fun createModelList(brand: String) = fakeModelList.map {
        "$brand - $it"
    }

    private fun createYearList(model: String) = fakeYearList.map {
        "$model - $it"
    }

    init {
        brandList = listOf(
            "brand1",
            "brand2",
            "brand3",
            "brand4",
            "brand5",
            "brand6",
            "brand7",
            "brand8",
            "brand9",
        )
        modelList = createModelList("brand1")
        yearList = createYearList("brand1 - model1")
    }
}

val fakeYearList = listOf(
    "1990",
    "1991",
    "1992",
    "1993",
    "1994",
    "1995",
    "1996",
    "1997",
    "1998",
    "1999",
)

val fakeModelList = listOf(
    "model1",
    "model2",
    "model3",
    "model4",
    "model5",
    "model6",
    "model7",
    "model8",
    "model9",
)

enum class DropDownType {
    Brand,
    Model,
    Year
}