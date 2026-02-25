package ci.nsu.moble.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ci.nsu.moble.main.ui.theme.PracticeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PracticeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TemperatureConverterApp()
                }
            }
        }
    }
}

@Composable
fun TemperatureConverterApp(
    viewModel: TemperatureViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Заголовок
        Text(
            text = "Конвертер температуры",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Поле Цельсия
        OutlinedTextField(
            value = uiState.celsius,
            onValueChange = { viewModel.onCelsiusChanged(it) },
            label = { Text("Градусы Цельсия (°C)") },
            isError = uiState.celsius.isNotBlank() && !uiState.isCelsiusValid,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
            supportingText = {
                if (uiState.celsius.isNotBlank() && !uiState.isCelsiusValid) {
                    Text("Введите корректное число")
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))


        Text(
            text = "↓",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.size(32.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Поле Фаренгейта
        OutlinedTextField(
            value = uiState.fahrenheit,
            onValueChange = { viewModel.onFahrenheitChanged(it) },
            label = { Text("Градусы Фаренгейта (°F)") },
            isError = uiState.fahrenheit.isNotBlank() && !uiState.isFahrenheitValid,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
            supportingText = {
                if (uiState.fahrenheit.isNotBlank() && !uiState.isFahrenheitValid) {
                    Text("Введите корректное число")
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Карточка с формулами
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Формулы конвертации:",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "°F = °C × 9/5 + 32")
                Text(text = "°C = (°F - 32) × 5/9")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка сброса
        Button(
            onClick = { viewModel.resetFields() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Сбросить все поля")
        }
    }
}