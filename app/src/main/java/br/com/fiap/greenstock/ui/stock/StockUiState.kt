package br.com.fiap.greenstock.ui.stock

import br.com.fiap.greenstock.data.model.Product

data class StockUiState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val searchText: String = "",
    val selectedCategory: String = "Todas",
    val error: String? = null
)