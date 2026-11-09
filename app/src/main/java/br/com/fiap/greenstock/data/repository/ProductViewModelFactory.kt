package br.com.fiap.greenstock.data.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.greenstock.ui.alerts.AlertsViewModel
import br.com.fiap.greenstock.ui.dashboard.DashboardViewModel
import br.com.fiap.greenstock.ui.reports.ReportsViewModel
import br.com.fiap.greenstock.ui.stock.StockViewModel

class ProductViewModelFactory(
    private val repository: ProductRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return when {
            modelClass.isAssignableFrom(DashboardViewModel::class.java) ->
                DashboardViewModel(repository) as T

            modelClass.isAssignableFrom(StockViewModel::class.java) ->
                StockViewModel(repository) as T

            modelClass.isAssignableFrom(AlertsViewModel::class.java) ->
                AlertsViewModel(repository) as T

            modelClass.isAssignableFrom(ReportsViewModel::class.java) ->
                ReportsViewModel(repository) as T

            else -> throw IllegalArgumentException(
                "ViewModel desconhecido: ${modelClass.name}"
            )
        }
    }
}