package br.com.fiap.greenstock.ui.reports

data class ReportsUiState(
    val isLoading: Boolean = false,
    val totalProducts: Int = 0,
    val totalQuantity: Int = 0,
    val lowStockProducts: Int = 0,
    val error: String? = null
)