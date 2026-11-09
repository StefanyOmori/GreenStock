package br.com.fiap.greenstock.ui.addproduct

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp

private val Green = Color(0xFF27834B)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    onBackClick: () -> Unit,
    onSaveClick: (
        name: String,
        category: String,
        quantity: Int,
        unit: String,
        expirationDate: String
    ) -> Unit
) {
    var name by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf("") }
    var quantity by rememberSaveable { mutableStateOf("") }
    var unit by rememberSaveable { mutableStateOf("unidades") }
    var expirationDate by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Adicionar produto",
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
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Inventory2,
                contentDescription = null,
                tint = Green
            )

            Text(
                text = "Informações do produto",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Preencha os dados para cadastrar um novo item no estoque.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    errorMessage = ""
                },
                label = { Text("Nome do produto") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = category,
                onValueChange = {
                    category = it
                    errorMessage = ""
                },
                label = { Text("Categoria") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = quantity,
                onValueChange = {
                    quantity = it.filter { character -> character.isDigit() }
                    errorMessage = ""
                },
                label = { Text("Quantidade") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )

            OutlinedTextField(
                value = unit,
                onValueChange = { unit = it },
                label = { Text("Unidade (ex.: unidades, kg, litros)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = expirationDate,
                onValueChange = { expirationDate = it },
                label = { Text("Validade (dd/MM/aaaa)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Button(
                onClick = {
                    val parsedQuantity = quantity.toIntOrNull()

                    errorMessage = when {
                        name.isBlank() -> "Informe o nome do produto."
                        category.isBlank() -> "Informe a categoria."
                        parsedQuantity == null || parsedQuantity <= 0 ->
                            "Informe uma quantidade maior que zero."
                        unit.isBlank() -> "Informe a unidade."
                        expirationDate.isBlank() ->
                            "Informe a validade do produto."
                        else -> ""
                    }

                    if (errorMessage.isEmpty()) {
                        onSaveClick(
                            name.trim(),
                            category.trim(),
                            parsedQuantity!!,
                            unit.trim(),
                            expirationDate.trim()
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Green
                )
            ) {
                Text("Salvar produto")
            }
        }
    }
}