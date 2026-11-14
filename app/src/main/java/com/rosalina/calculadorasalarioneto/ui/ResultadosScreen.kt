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
        val resultado = calcularSalario(datos)

        if (resultado == null) {
            Text("Introduce un salario bruto válido")
        } else {
            Text("Salario bruto: ${resultado.salarioBruto} €")
            Text("Seguridad Social: ${resultado.cotizacionSS} €")
            Text("Salario neto anual: ${resultado.salarioNeto} €")
            Text("Neto por paga: ${resultado.netoPorPaga} €")
        }
        Button(onClick = onVolver) { Text("Volver") }
    }
}