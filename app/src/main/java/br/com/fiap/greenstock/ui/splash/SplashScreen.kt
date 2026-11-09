package br.com.fiap.greenstock.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

private val Green = Color(0xFF27834B)

@Composable
fun SplashScreen(
    onTimeout: () -> Unit
) {

    LaunchedEffect(Unit) {
        delay(2000)
        onTimeout()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Green),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            imageVector = Icons.Default.Eco,
            contentDescription = "Logo GreenStock",
            modifier = Modifier.size(100.dp),
            tint = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "GreenStock",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Estoque inteligente, futuro sustentável.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
    }
}