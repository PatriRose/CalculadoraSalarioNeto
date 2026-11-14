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

@Composable
fun FormularioScreen(modifier: Modifier = Modifier) {

    var salarioBruto by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var hijos by remember { mutableStateOf("") }
    var estadoCivil by remember { mutableStateOf("Soltero/a") }
    var pagas by remember { mutableStateOf(value = "12") }
    var grupoProfesional by remember {mutableStateOf(value = "1")}
    var gradoDiscapacidad by remember { mutableStateOf(value = "Sin discapacidad") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Calculadora de salario neto",
            style = MaterialTheme.typography.headlineSmall
        )
        CampoNumerico(
            valor = salarioBruto,
            { salarioBruto = it },
            etiqueta = "Salario bruto anual (€)"
        )
        CampoNumerico(
            valor = edad,
            { edad = it },
            etiqueta = "Edad"
        )
        CampoNumerico(
            valor = hijos,
            { hijos = it },
            etiqueta = "Número de hijos"
        )
        CampoDesplegable(
            seleccion = estadoCivil,
            onSeleccion = {estadoCivil = it },
            opciones = listOf("Soltero/a", "Casado/a", "Divorciado/a", "Viudo/a"),
            etiqueta = "Estado Civil"
        )
        CampoDesplegable(
            seleccion = pagas,
            onSeleccion = { pagas = it },
            opciones = listOf("12", "14", "14+Var"),
            etiqueta = "Número de pagas"
        )
        CampoDesplegable(
            seleccion = grupoProfesional,
            onSeleccion = { grupoProfesional = it },
            opciones = (1 .. 11).map {"Grupo $it"},
            etiqueta = "Grupo Profesional"
        )
        CampoDesplegable(
            seleccion = gradoDiscapacidad,
            onSeleccion = { gradoDiscapacidad = it },
            opciones = listOf("Sin discapacidad", "33% - 64%", "65% o más"),
            etiqueta = "Grado Discapacidad"
        )
    }
}

@Composable
fun CampoNumerico (
    valor: String,
    onValorChange: (String) ->Unit,
    etiqueta: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValorChange,
        label = {Text(etiqueta)},
        keyboardOptions = KeyboardOptions (keyboardType = KeyboardType.Number),
        singleLine = true,
        modifier = modifier.fillMaxWidth()
    )
}

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
            label = { Text(etiqueta)},
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = desplegado)},
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = desplegado,
            onDismissRequest = {desplegado = false}
        ){
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = {Text(opcion)},
                    onClick = {
                        onSeleccion(opcion)
                        desplegado = false
                    }
                )
            }
        }
    }
}