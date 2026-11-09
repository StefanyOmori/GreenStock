package br.com.fiap.greenstock.app.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.fiap.greenstock.data.model.Product
import br.com.fiap.greenstock.data.remote.RetrofitInstance
import br.com.fiap.greenstock.data.repository.ProductRepository
import br.com.fiap.greenstock.data.repository.ProductViewModelFactory
import br.com.fiap.greenstock.navigation.Routes
import br.com.fiap.greenstock.ui.addproduct.AddProductScreen
import br.com.fiap.greenstock.ui.alerts.AlertsScreen
import br.com.fiap.greenstock.ui.alerts.AlertsUiState
import br.com.fiap.greenstock.ui.dashboard.DashboardScreen
import br.com.fiap.greenstock.ui.dashboard.DashboardUiState
import br.com.fiap.greenstock.ui.details.DetailsScreen
import br.com.fiap.greenstock.ui.onboarding.OnboardingPreferences
import br.com.fiap.greenstock.ui.onboarding.OnboardingScreen
import br.com.fiap.greenstock.ui.reports.ReportsScreen
import br.com.fiap.greenstock.ui.reports.ReportsViewModel
import br.com.fiap.greenstock.ui.splash.SplashScreen
import br.com.fiap.greenstock.ui.stock.StockScreen
import br.com.fiap.greenstock.ui.stock.StockViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val context = LocalContext.current

    val preferences = remember {
        OnboardingPreferences(context)
    }

    val repository = remember {
        ProductRepository(RetrofitInstance.api)
    }

    val factory = remember {
        ProductViewModelFactory(repository)
    }

    val stockViewModel: StockViewModel = viewModel(
        factory = factory
    )

    var selectedProduct by remember {
        mutableStateOf<Product?>(null)
    }

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {

        composable(Routes.SPLASH) {
            SplashScreen(
                onTimeout = {
                    val destination = if (
                        preferences.isOnboardingCompleted()
                    ) {
                        Routes.DASHBOARD
                    } else {
                        Routes.ONBOARDING
                    }

                    navController.navigate(destination) {
                        popUpTo(Routes.SPLASH) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Routes.ONBOARDING) {
            OnboardingScreen(
                onFinish = {
                    preferences.setOnboardingCompleted()

                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.ONBOARDING) {
                            inclusive = true
                        }
                    }
                }
            )
        }


        composable(Routes.DASHBOARD) {

            val stockState = stockViewModel.uiState

            val dashboardState = DashboardUiState(
                isLoading = stockState.isLoading,
                products = stockState.products,
                totalProducts = stockState.products.size,
                lowStockProducts = stockState.products.count {
                    it.quantity <= 10
                },
                error = stockState.error
            )

            DashboardScreen(
                uiState = dashboardState,

                onNavigateToStock = {
                    navController.navigate(Routes.STOCK)
                },

                onNavigateToAlerts = {
                    navController.navigate(Routes.ALERTS)
                },

                onNavigateToReports = {
                    navController.navigate(Routes.REPORTS)
                },

                onAddProduct = {
                    navController.navigate(Routes.ADD_PRODUCT)
                },

                onRefresh = {
                    stockViewModel.loadProducts()
                }
            )
        }


        composable(Routes.STOCK) {

            StockScreen(
                uiState = stockViewModel.uiState,

                onBackClick = {
                    navController.popBackStack()
                },

                onSearchChange = {
                    stockViewModel.updateSearch(it)
                },

                onCategoryChange = {
                    stockViewModel.updateCategory(it)
                },

                onAddProduct = {
                    navController.navigate(Routes.ADD_PRODUCT)
                },

                onProductClick = { productId ->
                    selectedProduct = stockViewModel.uiState.products
                        .find { it.id == productId }

                    navController.navigate(
                        "${Routes.DETAILS}/$productId"
                    )
                },

                onRefresh = {
                    stockViewModel.loadProducts()
                }
            )
        }


        composable(Routes.ALERTS) {

            val stockState = stockViewModel.uiState
            val products = stockViewModel.allProducts

            val alertsUiState = AlertsUiState(
                isLoading = stockState.isLoading,

                productsWithoutExpiration = products.filter {
                    it.expirationDate.isBlank() ||
                            it.expirationDate.equals("Não informada", ignoreCase = true)
                },

                lowStockProducts = products.filter {
                    it.quantity <= 10
                },

                productsNearExpiration = products.filter {
                    isExpiringWithinSevenDays(it.expirationDate)
                },

                error = stockState.error
            )

            AlertsScreen(
                uiState = alertsUiState,

                onBackClick = {
                    navController.popBackStack()
                },

                onRefresh = {
                    stockViewModel.loadProducts()
                },

                onProductClick = { productId ->
                    selectedProduct = products.find {
                        it.id == productId
                    }

                    navController.navigate(
                        "${Routes.DETAILS}/$productId"
                    )
                }
            )
        }

        composable(Routes.REPORTS) {

            val reportsViewModel: ReportsViewModel = viewModel(
                factory = factory
            )

            ReportsScreen(
                uiState = reportsViewModel.uiState,

                onBackClick = {
                    navController.popBackStack()
                },

                onRefresh = {
                    reportsViewModel.loadReport()
                }
            )
        }

        composable(Routes.ADD_PRODUCT) {
            AddProductScreen(
                onBackClick = {
                    navController.popBackStack()
                },

                onSaveClick = {
                        name,
                        category,
                        quantity,
                        unit,
                        expirationDate ->

                    stockViewModel.addProduct(
                        name = name,
                        category = category,
                        quantity = quantity,
                        unit = unit,
                        expirationDate = expirationDate
                    )

                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "${Routes.DETAILS}/{productId}",
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.IntType
                }
            )
        ) {
            DetailsScreen(
                product = selectedProduct,

                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun isExpiringWithinSevenDays(expirationDate: String): Boolean {
    if (
        expirationDate.isBlank() ||
        expirationDate.equals("Não informada", ignoreCase = true)
    ) {
        return false
    }

    return try {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val expiration = LocalDate.parse(expirationDate, formatter)
        val today = LocalDate.now()

        val days = ChronoUnit.DAYS.between(today, expiration)

        days in 0..7
    } catch (e: Exception) {
        false
    }
}
