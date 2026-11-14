package com.rosalina.calculadorasalarioneto.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResultadosScreen(
    datos: DatosFormulario,
    onVolver: () -> Unit,
    modifier: Modifier = Modifier) {

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Resultados", style = MaterialTheme.typography.headlineSmall)
        Text("Salario bruto: ${datos.salarioBruto} €")
        Text("Edad: ${datos.edad} años")
        Button(onClick = onVolver) { Text("Volver") }
    }
}