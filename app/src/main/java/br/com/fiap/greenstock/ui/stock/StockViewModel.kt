package br.com.fiap.greenstock.ui.stock

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.greenstock.data.model.Product
import br.com.fiap.greenstock.data.repository.ProductRepository
import kotlinx.coroutines.launch

class StockViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    var uiState by mutableStateOf(StockUiState())
        private set

    var allProducts = emptyList<Product>()
        private set
    private var localProducts = emptyList<Product>()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true,
                error = null
            )

            try {
                allProducts = repository.getProducts() + localProducts
                applyFilters()
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = "Não foi possível carregar o estoque."
                )
            }
        }
    }

    fun addProduct(
        name: String,
        category: String,
        quantity: Int,
        unit: String,
        expirationDate: String
    ) {
        val product = Product(
            id = -(localProducts.size + 1),
            name = name,
            category = category,
            quantity = quantity,
            unit = unit,
            expirationDate = expirationDate,
            status = "Validade cadastrada",
            image = null
        )

        localProducts = localProducts + product
        allProducts = allProducts + product

        applyFilters()
    }

    fun updateSearch(text: String) {
        uiState = uiState.copy(searchText = text)
        applyFilters()
    }

    fun updateCategory(category: String) {
        uiState = uiState.copy(selectedCategory = category)
        applyFilters()
    }

    private fun applyFilters() {
        val filteredProducts = allProducts.filter { product ->

            val matchesSearch = product.name.contains(
                uiState.searchText,
                ignoreCase = true
            )

            val matchesCategory =
                uiState.selectedCategory == "Todas" ||
                        product.category.equals(
                            uiState.selectedCategory,
                            ignoreCase = true
                        )

            matchesSearch && matchesCategory
        }

        uiState = uiState.copy(
            isLoading = false,
            products = filteredProducts
        )
    }
}