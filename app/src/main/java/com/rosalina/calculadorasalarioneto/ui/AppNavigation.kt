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

/**
 * Gestiona la navegación entre las distintas pantallas de la aplicación y mantiene
 * el estado compartido de los datos del formulario.
 */
@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    // El estado se guarda aquí, y no en cada pantalla, para que no se pierda al navegar
    // y para poder pasar los datos de la pantalla 1 a la pantalla 2 (state hoisting).
    var datos by remember { mutableStateOf(DatosFormulario()) }

    // Contenedor que define las rutas disponibles y muestra la pantalla actual
    NavHost(
        navController = navController,
        startDestination = "formulario",
        modifier = modifier
    ) {
        // Pantalla 1: Formulario de datos
        composable("formulario") {
            FormularioScreen(
                datos = datos,
                onDatosChange = { datos = it },
                // apila la pantalla 2
                onCalcular = { navController.navigate("resultados") }
            )
        }
        // Pantalla 2: Visualización de los resultados desglosados
        composable("resultados") {
            ResultadosScreen(
                datos = datos,
                // vuelve a la pantalla anterior
                onVolver = { navController.popBackStack() }
            )
        }
    }
}
