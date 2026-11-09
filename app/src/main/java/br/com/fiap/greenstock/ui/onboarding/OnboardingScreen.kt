package br.com.fiap.greenstock.ui.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

private val Green = Color(0xFF27834B)

@Composable
fun OnboardingScreen(
    onFinish: () -> Unit
) {

    var page by remember {
        mutableIntStateOf(0)
    }

    val titles = listOf(
        "Controle seu estoque",
        "Não perca a validade",
        "Reduza o desperdício"
    )

    val descriptions = listOf(
        "Cadastre seus alimentos e acompanhe todos os produtos em um só lugar.",
        "Identifique produtos próximos do vencimento e evite perdas.",
        "Acompanhe seu estoque e tome decisões mais sustentáveis."
    )

    val icons = listOf(
        Icons.Default.Inventory2,
        Icons.Default.NotificationsActive,
        Icons.Default.Eco
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = "GreenStock",
            style = MaterialTheme.typography.headlineMedium,
            color = Green
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {

            Icon(
                imageVector = icons[page],
                contentDescription = null,
                modifier = Modifier.size(110.dp),
                tint = Green
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = titles[page],
                style = MaterialTheme.typography.headlineSmall,
                color = Green
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = descriptions[page],
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(3) { index ->
                    Icon(
                        imageVector = Icons.Default.Circle,
                        contentDescription = null,
                        modifier = Modifier.size(10.dp),
                        tint = if (page == index) Green
                        else Color.LightGray
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (page > 0) {
                OutlinedButton(
                    onClick = {
                        page--
                    }
                ) {
                    Text("Voltar")
                }
            } else {
                Spacer(modifier = Modifier.width(80.dp))
            }

            Button(
                onClick = {
                    if (page < 2) {
                        page++
                    } else {
                        onFinish()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Green
                )
            ) {
                Text(
                    if (page == 2) "Começar"
                    else "Avançar"
                )
            }
        }
    }
}