package com.rosalina.calculadorasalarioneto.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

/**
 * Pantalla 1: Visualización de los diferentes datos a cumplimentar.
 * Definimos estos valores en DatosFormulario para no tener que repetirlos
 */
@Composable
fun FormularioScreen(
    datos: DatosFormulario,
    onDatosChange: (DatosFormulario) -> Unit,
    onCalcular: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Calculadora de salario neto",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        CampoNumerico(
            valor = datos.salarioBruto,
            { onDatosChange(datos.copy(salarioBruto = it)) },
            etiqueta = "Salario bruto anual (€)"
        )
        CampoNumerico(
            valor = datos.edad,
            { onDatosChange(datos.copy(edad = it)) },
            etiqueta = "Edad"
        )
        CampoNumerico(
            valor = datos.hijos,
            { onDatosChange(datos.copy(hijos = it)) },
            etiqueta = "Número de hijos"
        )
        CampoDesplegable(
            seleccion = datos.estadoCivil,
            onSeleccion = { onDatosChange(datos.copy(estadoCivil = it)) },
            opciones = listOf("Soltero/a", "Casado/a", "Divorciado/a", "Viudo/a"),
            etiqueta = "Estado Civil"
        )
        CampoDesplegable(
            seleccion = datos.pagas,
            onSeleccion = { onDatosChange(datos.copy(pagas = it)) },
            opciones = listOf("12", "14"),
            etiqueta = "Número de pagas"
        )
        CampoDesplegable(
            seleccion = datos.grupoProfesional,
            onSeleccion = { onDatosChange(datos.copy(grupoProfesional = it)) },
            opciones = (1..11).map { "Grupo $it" },
            etiqueta = "Grupo Profesional"
        )
        CampoDesplegable(
            seleccion = datos.gradoDiscapacidad,
            onSeleccion = { onDatosChange(datos.copy(gradoDiscapacidad = it)) },
            opciones = listOf("Sin discapacidad", "33% - 64%", "65% o más"),
            etiqueta = "Grado Discapacidad"
        )
        Button(onClick = onCalcular, modifier = Modifier.fillMaxWidth()) {
            Text("Calcular")
        }
    }
}

/**
 * Configuración de los datos a tratar de origen numérico
 */
@Composable
fun CampoNumerico(
    valor: String,
    onValorChange: (String) -> Unit,
    etiqueta: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValorChange,
        label = { Text(etiqueta) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        modifier = modifier.fillMaxWidth()
    )
}

/**
 * Datos a tratar que proceden de desplegables con varias opciones
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampoDesplegable(
    seleccion: String,
    onSeleccion: (String) -> Unit,
    opciones: List<String>,
    etiqueta: String,
    modifier: Modifier = Modifier
) {
    var desplegado by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = desplegado,
        onExpandedChange = { desplegado = it },
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = seleccion,
            onValueChange = {},
            readOnly = true,
            label = { Text(etiqueta) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = desplegado) },
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = desplegado,
            onDismissRequest = { desplegado = false }
        ) {
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = {
                        onSeleccion(opcion)
                        desplegado = false
                    }
                )
            }
        }
    }
}