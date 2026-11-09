package br.com.fiap.greenstock.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private val Green = Color(0xFF27834B)
private val LightGreen = Color(0xFFEAF5ED)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    uiState: DashboardUiState,
    onNavigateToStock: () -> Unit,
    onNavigateToAlerts: () -> Unit,
    onNavigateToReports: () -> Unit,
    onAddProduct: () -> Unit,
    onRefresh: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "GreenStock",
                            fontWeight = FontWeight.Bold,
                            color = Green
                        )
                        Text(
                            text = "Gestão inteligente de alimentos",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        containerColor = Color(0xFFF7F9F7)
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            Text(
                text = "Visão geral",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Acompanhe seus produtos e reduza o desperdício.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Green
                )
            }

            uiState.error?.let { error ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFEEEE)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(error, color = Color.Red)

                        Button(onClick = onRefresh) {
                            Icon(
                                Icons.Default.Refresh,
                                contentDescription = null
                            )
                            Text(" Tentar novamente")
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DashboardInfoCard(
                    title = "Produtos",
                    value = uiState.totalProducts.toString(),
                    icon = {
                        Icon(
                            Icons.Default.Inventory2,
                            contentDescription = null,
                            tint = Green
                        )
                    },
                    modifier = Modifier.weight(1f)
                )

                DashboardInfoCard(
                    title = "Estoque baixo",
                    value = uiState.lowStockProducts.toString(),
                    icon = {
                        Icon(
                            Icons.Default.NotificationsActive,
                            contentDescription = null,
                            tint = Color(0xFFE69A24)
                        )
                    },
                    modifier = Modifier.weight(1f)
                )
            }

            Text(
                text = "Acesso rápido",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            DashboardAction(
                title = "Visualizar estoque",
                description = "Consulte e pesquise seus produtos.",
                icon = {
                    Icon(
                        Icons.Default.Inventory2,
                        contentDescription = null
                    )
                },
                onClick = onNavigateToStock
            )

            DashboardAction(
                title = "Alertas",
                description = "Confira produtos que precisam de atenção.",
                icon = {
                    Icon(
                        Icons.Default.NotificationsActive,
                        contentDescription = null
                    )
                },
                onClick = onNavigateToAlerts
            )

            DashboardAction(
                title = "Relatórios",
                description = "Acompanhe os indicadores do estoque.",
                icon = {
                    Icon(
                        Icons.Default.Assessment,
                        contentDescription = null
                    )
                },
                onClick = onNavigateToReports
            )

            Button(
                onClick = onAddProduct,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Green
                ),
                contentPadding = PaddingValues(16.dp)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null
                )
                Text("  Cadastrar produto")
            }

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = LightGreen
                )
            ) {
                Text(
                    text = "Pequenas atitudes fazem a diferença. " +
                            "Acompanhe seu estoque e ajude a reduzir o desperdício de alimentos.",
                    modifier = Modifier.padding(18.dp),
                    color = Green,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun DashboardInfoCard(
    title: String,
    value: String,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            icon()

            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}

@Composable
private fun DashboardAction(
    title: String,
    description: String,
    icon: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            icon()

            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = description,
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}