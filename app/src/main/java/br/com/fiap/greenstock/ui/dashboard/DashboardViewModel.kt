package br.com.fiap.greenstock.ui.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.greenstock.data.repository.ProductRepository
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    var uiState by mutableStateOf(DashboardUiState())
        private set

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
                val products = repository.getProducts()

                uiState = DashboardUiState(
                    products = products,
                    totalProducts = products.size,
                    lowStockProducts = products.count {
                        it.quantity <= 10
                    }
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = "Não foi possível carregar os produtos."
                )
            }
        }
    }
}