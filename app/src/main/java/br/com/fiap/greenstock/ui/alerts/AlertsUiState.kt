package br.com.fiap.greenstock.ui.alerts

import br.com.fiap.greenstock.data.model.Product

data class AlertsUiState(
    val isLoading: Boolean = false,
    val productsWithoutExpiration: List<Product> = emptyList(),
    val lowStockProducts: List<Product> = emptyList(),
    val productsNearExpiration: List<Product> = emptyList(),
    val error: String? = null
)
