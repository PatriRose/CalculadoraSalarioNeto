package com.rosalina.calculadorasalarioneto.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


/**
 * Pantalla 2: muestra el resultado del cálculo del salario neto. *
 * @param datos datos introducidos en el formulario (pantalla 1)
 * @param onVolver acción al pulsar el botón de "Volver"
 */
@Composable
fun ResultadosScreen(
    datos: DatosFormulario,
    onVolver: () -> Unit,
    modifier: Modifier = Modifier
) {

    // El resultado del calculo proviene de CalculoSalario, en este caso solo muestra resultado.
    val resultado = calcularSalario(datos)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Resultados", style = MaterialTheme.typography.headlineSmall)

        if (resultado == null) {
            // calcularSalario devuelve null si el salario no es válido.
            Text("Introduce un salario bruto válido")
        } else {
            // Tipo efectivo: qué parte del salario bruto se va en IRPF (evitar dividir por 0)
            val tipoEfectivo = if (resultado.salarioBruto > 0) {
                resultado.retencionIrpf / resultado.salarioBruto * 100
            } else 0.0

            // Tarjeta 1: valores del bruto al neto
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilaResultado("Salario bruto anual", formatoEuros(resultado.salarioBruto))
                    FilaResultado("Seguridad Social", "- " + formatoEuros(resultado.cotizacionSS))
                    FilaResultado("Retención IRPF", "- " + formatoEuros(resultado.retencionIrpf))
                    HorizontalDivider()
                    FilaResultado(
                        "Salario neto anual",
                        formatoEuros(resultado.salarioNeto),
                        destacado = true
                    )
                    FilaResultado(
                        "Neto por paga",
                        formatoEuros(resultado.netoPorPaga),
                        destacado = true
                    )
                }
            }

            //Tarjeta 2: detalle del IRPF
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilaResultado("Tipo efectivo de IRPF", formatoPorcentaje(tipoEfectivo))
                    FilaResultado("Deducciones aplicadas", formatoEuros(resultado.deducciones))
                }
            }
        }
        Button(onClick = onVolver) { Text("Volver") }
    }
}

/**
 * Función para el formato de la fila dentro de las tarjetas de resultados.
 */
@Composable
private fun FilaResultado(etiqueta: String, valor: String, destacado: Boolean = false) {
    val estilo = if (destacado) MaterialTheme.typography.titleMedium
    else MaterialTheme.typography.bodyLarge
    val peso = if (destacado) FontWeight.Bold
    else FontWeight.Normal

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = etiqueta, style = estilo, fontWeight = peso)
        Text(text = valor, style = estilo, fontWeight = peso)
    }
}