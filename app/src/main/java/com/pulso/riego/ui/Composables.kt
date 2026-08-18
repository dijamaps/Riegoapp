package com.pulso.riego.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pulso.riego.viewmodel.LoginUiState
import com.pulso.riego.viewmodel.LoginViewModel

@Composable
fun LandingScreen(onLoginClick: () -> Unit, onFeaturesClick: () -> Unit) {
    val gradient = Brush.linearGradient(listOf(Color(0xFF667EEA), Color(0xFF764BA2)))
    Surface(modifier = Modifier.fillMaxSize(), color = Color.Transparent) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text("🌾 Pulso de Riego", style = MaterialTheme.typography.headlineLarge, color = Color.White)
            Text("Sistema Inteligente de Monitoreo y Gestión de Riego", color = Color.White, modifier = Modifier.padding(8.dp))
            Spacer(modifier = Modifier.height(24.dp))
            Card(shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Revoluciona tu Sistema de Riego", color = Color(0xFF667EEA), style = MaterialTheme.typography.headlineSmall)
                    Text("Monitorea en tiempo real el drenaje de tus lotes y optimiza tu consumo de agua", color = Color.DarkGray, modifier = Modifier.padding(8.dp))
                    Spacer(modifier = Modifier.height(12.dp))
                    Row {
                        Button(onClick = onFeaturesClick, modifier = Modifier.padding(8.dp)) { Text("Conocer Características") }
                        OutlinedButton(onClick = onLoginClick, modifier = Modifier.padding(8.dp)) { Text("Ingresar") }
                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen(viewModel: LoginViewModel, onLoginSuccess: () -> Unit) {
    val state by viewModel.uiState.collectAsState()
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(50.dp))
        Text("Login", style = MaterialTheme.typography.headlineMedium)
        OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Usuario") }, modifier = Modifier.fillMaxWidth().padding(8.dp))
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Contraseña") }, modifier = Modifier.fillMaxWidth().padding(8.dp), singleLine = true)
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = { viewModel.login(username.trim(), password) }) { Text("Ingresar") }

        when (state) {
            is LoginUiState.Loading -> CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            is LoginUiState.Error -> Text((state as LoginUiState.Error).message, color = Color.Red)
            is LoginUiState.Success -> {
                LaunchedEffect(Unit) { onLoginSuccess() }
            }
            else -> {}
        }
    }
}

@Composable
fun HomeScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Estados de Drenaje", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatusCard("ÓPTIMO", "✅", "10% - 40%", Color(0xFF00B894))
            StatusCard("DÉFICIT", "⚠️", "< 10%", Color(0xFFFF9500))
            StatusCard("EXCESO", "🚫", "> 40%", Color(0xFFFF6B6B))
        }
    }
}

@Composable
fun StatusCard(title: String, icon: String, range: String, color: Color) {
    Card(modifier = Modifier.weight(1f)) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(icon, style = MaterialTheme.typography.headlineLarge)
            Text(title, color = color, style = MaterialTheme.typography.titleMedium)
            Text(range, style = MaterialTheme.typography.bodySmall)
        }
    }
}
