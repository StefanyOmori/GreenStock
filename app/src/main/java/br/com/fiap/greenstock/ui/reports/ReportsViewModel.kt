package br.com.fiap.greenstock.ui.reports

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.greenstock.data.repository.ProductRepository
import kotlinx.coroutines.launch

class ReportsViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    var uiState by mutableStateOf(ReportsUiState())
        private set

    init {
        loadReport()
    }

    fun loadReport() {
        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true,
                error = null
            )

            try {
                val products = repository.getProducts()

                uiState = ReportsUiState(
                    totalProducts = products.size,
                    totalQuantity = products.sumOf { it.quantity },
                    lowStockProducts = products.count { it.quantity <= 10 }
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = "Não foi possível carregar os relatórios."
                )
            }
        }
    }
}