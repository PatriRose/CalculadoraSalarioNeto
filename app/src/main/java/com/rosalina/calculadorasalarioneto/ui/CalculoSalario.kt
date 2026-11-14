package com.rosalina.calculadorasalarioneto.ui

import java.util.Locale
data class ResultadoSalario(
    val salarioBruto: Double,
    val cotizacionSS: Double,
    val retencionIrpf: Double,
    val deducciones: Double,
    val salarioNeto: Double,
    val netoPorPaga: Double,
)

private val tramosIrpf = listOf(
    12450.0 to 0.19,
    20200.0 to 0.24,
    35200.0 to 0.30,
    60000.0 to 0.37,
    300000.0 to 0.45,
    Double.MAX_VALUE to 0.47
)

fun calcularSalario(datos: DatosFormulario): ResultadoSalario? {
    val bruto = datos.salarioBruto.toDoubleOrNull() ?: return null
    val pagas = datos.pagas.toInt()
    val cotizacionSS = bruto * 0.0648
    val gastosDeducibles = 2000.0
    val baseIrpf = maxOf(bruto - cotizacionSS - gastosDeducibles, 0.0)
    val edad = datos.edad.toIntOrNull() ?: 0
    val hijos = datos.hijos.toIntOrNull() ?: 0

    val minimoTotal = 5550.0 +
            minimoPorEdad(edad) +
            minimoPorHijos(hijos) +
            minimoPorDiscapacidad(datos.gradoDiscapacidad)

    val deducciones = cuotaPorTramos(minimoTotal)
    val retencionIrpf = maxOf(cuotaPorTramos(baseIrpf) - deducciones, 0.0)

    val neto = bruto - cotizacionSS - retencionIrpf

    return ResultadoSalario(
        bruto,
        cotizacionSS,
        retencionIrpf,
        deducciones,
        neto,
        neto / pagas)
}

fun formatoEuros(valor: Double): String =
    String.format(Locale.forLanguageTag("es-ES"), "%,.2f €", valor)

fun cuotaPorTramos(base: Double): Double {
    var cuota = 0.0
    var limiteInferior = 0.0
    for ((limiteSuperior, tipo) in tramosIrpf) {
        if (base > limiteInferior) {
            val parte = minOf(base, limiteSuperior) - limiteInferior
            cuota += parte * tipo
        }
        limiteInferior = limiteSuperior
    }
    return cuota
}

private fun minimoPorEdad(edad: Int): Double = when {
    edad > 75 -> 1150.0 + 1400.0
    edad > 65 -> 1150.0
    else -> 0.0
}

private fun minimoPorHijos(hijos: Int): Double {
    var total = 0.0
    for (i in 1..hijos) {
        total += when (i) {
            1 -> 2400.0
            2 -> 2700.0
            3 -> 4000.0
            else -> 4500.0
        }
    }
    return total
}

private fun minimoPorDiscapacidad(discapacidad: String): Double = when (discapacidad) {
    "33% - 64%" -> 3000.0
    "65% o más" -> 9000.0
    else -> 0.0
}