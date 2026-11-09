package br.com.fiap.greenstock.ui.alerts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import br.com.fiap.greenstock.data.model.Product

private val Green = Color(0xFF27834B)
private val LightYellow = Color(0xFFFFF5DF)
private val Orange = Color(0xFFE69A24)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertsScreen(
    uiState: AlertsUiState,
    onRefresh: () -> Unit,
    onProductClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    val totalAlerts =
        uiState.productsWithoutExpiration.size +
                uiState.lowStockProducts.size +
                uiState.productsNearExpiration.size

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Alertas",
                        color = Green,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar"
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
                .padding(20.dp)
        ) {
            Text(
                text = "Atenção ao seu estoque!",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Acompanhe os produtos que precisam de atenção.",
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = LightYellow
                )
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.NotificationsActive,
                        contentDescription = null,
                        tint = Orange
                    )

                    Text(
                        text = "$totalAlerts alertas encontrados",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF805400)
                    )

                    Text(
                        text = "Confira os produtos que precisam de acompanhamento.",
                        color = Color(0xFF805400),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Green
                )
            } else if (uiState.error != null) {
                Text(
                    text = uiState.error,
                    color = Color.Red
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onRefresh,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Green
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null
                    )
                    Text(" Tentar novamente")
                }
            } else if (totalAlerts == 0) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Green
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text("Nenhum alerta no momento.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    if (uiState.lowStockProducts.isNotEmpty()) {
                        item {
                            Text(
                                text = "Estoque baixo",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        items(
                            items = uiState.lowStockProducts,
                            key = { "low-${it.id}" }
                        ) { product ->
                            AlertProductCard(
                                product = product,
                                alertLabel = "Estoque baixo",
                                onClick = { onProductClick(product.id) }
                            )
                        }
                    }

                    if (uiState.productsNearExpiration.isNotEmpty()) {
                        item {
                            Text(
                                text = "Próximos do vencimento",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        items(
                            items = uiState.productsNearExpiration,
                            key = { "expiry-${it.id}" }
                        ) { product ->
                            AlertProductCard(
                                product = product,
                                alertLabel = "Próximo do vencimento",
                                onClick = { onProductClick(product.id) }
                            )
                        }
                    }

                    if (uiState.productsWithoutExpiration.isNotEmpty()) {
                        item {
                            Text(
                                text = "Validade não informada",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        items(
                            items = uiState.productsWithoutExpiration,
                            key = { "missing-${it.id}" }
                        ) { product ->
                            AlertProductCard(
                                product = product,
                                alertLabel = "Validade não cadastrada",
                                onClick = { onProductClick(product.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AlertProductCard(
    product: Product,
    alertLabel: String,
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
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = product.name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = alertLabel,
                color = Orange,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Categoria: ${product.category}",
                color = Color.Gray
            )

            Text(
                text = "Validade: ${product.expirationDate}",
                color = Color.Gray
            )

            Text(
                text = "Quantidade: ${product.quantity} ${product.unit}",
                color = Color.Gray
            )
        }
    }
}
