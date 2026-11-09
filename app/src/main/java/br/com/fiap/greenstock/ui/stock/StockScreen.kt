package br.com.fiap.greenstock.ui.stock

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import br.com.fiap.greenstock.ui.components.ProductCard
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState

private val Green = Color(0xFF27834B)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockScreen(
    uiState: StockUiState,
    onSearchChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onAddProduct: () -> Unit,
    onProductClick: (Int) -> Unit,
    onRefresh: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Meu estoque",
                        fontWeight = FontWeight.Bold,
                        color = Green
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        onBackClick()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
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
                text = "Produtos cadastrados",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Consulte e organize os alimentos do seu estoque.",
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(18.dp))

            OutlinedTextField(
                value = uiState.searchText,
                onValueChange = onSearchChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Buscar produto...")
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Buscar"
                    )
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Categorias",
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val categories = listOf(
                    "Todas" to "Todas",
                    "Alimentos" to "groceries",
                    "Frutas" to "Frutas",
                    "Verduras e legumes" to "Verduras e legumes",
                    "Grãos e cereais" to "Grãos e cereais",
                    "Laticínios" to "Laticínios",
                    "Bebidas" to "Bebidas",
                    "Outros" to "Outros"
                )

                categories.forEach { (label, value) ->
                    FilterChip(
                        selected = uiState.selectedCategory == value,
                        onClick = {
                            onCategoryChange(value)
                        },
                        label = {
                            Text(label)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Green
                )
            }

            if (uiState.error != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = uiState.error,
                        color = Color.Red
                    )

                    Button(
                        onClick = onRefresh,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Green
                        )
                    ) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = null
                        )
                        Text(" Tentar novamente")
                    }
                }
            } else if (!uiState.isLoading) {

                if (uiState.products.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Inventory2,
                            contentDescription = null,
                            tint = Green
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Nenhum produto encontrado.",
                            fontWeight = FontWeight.Medium
                        )

                        Text(
                            text = "Experimente alterar sua busca ou filtro.",
                            color = Color.Gray
                        )
                    }
                } else {
                    Text(
                        text = "${uiState.products.size} produtos encontrados",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(
                            items = uiState.products,
                            key = { product -> product.id }
                        ) { product ->
                            ProductCard(
                                product = product,
                                onClick = {
                                    onProductClick(product.id)
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onAddProduct,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Green
                ),
                contentPadding = PaddingValues(14.dp)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null
                )
                Text("  Cadastrar produto")
            }
        }
    }
}