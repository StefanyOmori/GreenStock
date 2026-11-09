package br.com.fiap.greenstock.ui.dashboard

import br.com.fiap.greenstock.data.model.Product

data class DashboardUiState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val totalProducts: Int = 0,
    val lowStockProducts: Int = 0,
    val error: String? = null
)