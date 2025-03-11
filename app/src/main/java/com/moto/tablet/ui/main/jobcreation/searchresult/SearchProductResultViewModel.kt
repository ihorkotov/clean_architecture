package com.moto.tablet.ui.main.jobcreation.searchresult

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.moto.tablet.base.CartViewModel
import com.moto.tablet.data.CartRepository
import com.moto.tablet.data.GlobalRepository
import com.moto.tablet.model.PartProduct
import com.moto.tablet.model.fakePartProducts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchProductResultViewModel @Inject constructor(
    val cartRepository: CartRepository,
    val globalRepository: GlobalRepository,
) : CartViewModel(cartRepository) {
    private val searchKey = globalRepository.searchKey
    val query: String
        get() = searchKey?.queryString() ?: ""

    var showProgress: Boolean by mutableStateOf(false)

    var products: List<PartProduct> by mutableStateOf(listOf())
        private set

    private fun startSearch() {
        viewModelScope.launch {
            showProgress = true
            delay(1000)
            products = fakePartProducts()
            showProgress = false
        }
    }

    fun updateFullScreeImageList(images: List<String>) {
        globalRepository.fullScreenImages = images
    }

    fun selectProductDetail(product: PartProduct) {
        globalRepository.productForDetail = product
    }

    init {
        startSearch()
    }
}

class SearchKey(
    val query: String?,
    val brandQuery: BrandQuery?
) {
    fun queryString(): String {
        return when {
            query != null -> query
            brandQuery != null -> brandQuery.queryString()
            else -> ""
        }
    }
}

class BrandQuery(
    val brand: String,
    val model: String?,
    val year: String?
) {
    fun queryString(): String {
        return "$brand ${model ?: ""} ${year ?: ""}"
    }
}