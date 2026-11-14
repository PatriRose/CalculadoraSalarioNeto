package com.rosalina.calculadorasalarioneto.ui


import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    var datos by remember { mutableStateOf(DatosFormulario()) }

    NavHost(
        navController = navController,
        startDestination = "formulario",
        modifier = modifier
    ) {
        composable("formulario") {
            FormularioScreen(
                datos = datos,
                onDatosChange = { datos = it },
                onCalcular = { navController.navigate("resultados") }
            )
        }
        composable("resultados") {
            ResultadosScreen(
                datos = datos,
                onVolver = { navController.popBackStack() }
            )
        }
    }
}
