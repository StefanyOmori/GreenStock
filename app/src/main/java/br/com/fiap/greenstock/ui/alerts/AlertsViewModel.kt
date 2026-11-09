package br.com.fiap.greenstock.ui.alerts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.greenstock.data.repository.ProductRepository
import kotlinx.coroutines.launch

class AlertsViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    var uiState by mutableStateOf(AlertsUiState())
        private set

    init {
        loadAlerts()
    }

    fun loadAlerts() {
        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true,
                error = null
            )

            try {
                val products = repository.getProducts()

                uiState = AlertsUiState(
                    productsWithoutExpiration = products.filter {
                        it.expirationDate == "Não informada"
                    }
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = "Não foi possível carregar os alertas."
                )
            }
        }
    }
}