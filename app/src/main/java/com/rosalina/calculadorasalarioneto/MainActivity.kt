package com.rosalina.calculadorasalarioneto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.rosalina.calculadorasalarioneto.ui.AppNavigation
import com.rosalina.calculadorasalarioneto.ui.theme.CalculadoraSalarioNetoTheme

/**
 * Punto de entrada de la aplicación.
 * Se encarga de inicializar la interfaz de usuario con Jetpack Compose, aplicar el tema visual
 * y cargar el contenedor principal de navegación.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Habilita el diseño de borde a borde así se aprovecha toda la pantalla.
        enableEdgeToEdge()
        setContent {
            CalculadoraSalarioNetoTheme {
                // innerPadding evita que el contenido quede oculto bajo las barras del sistema
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(modifier = Modifier.padding(innerPadding))

                }
            }
        }
    }
}

