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

/**
 * Porcentaje que se debe aplicar (compuesto) por tramos.
 * Ejemplo: Si alguien gana 30.000€, se le aplica un 19% a los primeros 12.450€,
 * a los 7.750 se el aplica el 24% y al restante 9.800€ se le aplica 30%
 */
private val tramosIrpf = listOf(
    12450.0 to 0.19,
    20200.0 to 0.24,
    35200.0 to 0.30,
    60000.0 to 0.37,
    300000.0 to 0.45,
    Double.MAX_VALUE to 0.47
)

/**
 * Calcula el salario neto a partir de los datos del formulario.
 *
 * Pasos:
 * 1) se resta la Seguridad Social y los gastos deducibles para obtener la base;
 * 2) se aplica la escala de tramos del IRPF;
 * 3) se resta el ahorro del mínimo * personal y familiar (hijos, edad, discapacidad).
 *
 * @return el resultado, o null si el salario introducido no es un número válido.
 */
fun calcularSalario(datos: DatosFormulario): ResultadoSalario? {
    val bruto = datos.salarioBruto.replace(',', '.').toDoubleOrNull() ?: return null
    if (bruto <= 0) return null
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
        neto / pagas
    )
}

/**
 * Formatear los resultados al sistema español con dos dígitos
 */
fun formatoEuros(valor: Double): String =
    String.format(Locale.forLanguageTag("es-ES"), "%,.2f €", valor)

/**
 * Formatear porcentaje al sisstema español
 */
fun formatoPorcentaje(valor: Double): String =
    String.format(Locale.forLanguageTag("es-ES"), "%.2f %%", valor)

/**
 * función para evaluar las cuotas por rango inferior y superior
 */
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

/**
 * Distinguir las deducciones por rangos de edad
 */
private fun minimoPorEdad(edad: Int): Double = when {
    edad > 75 -> 1150.0 + 1400.0
    edad > 65 -> 1150.0
    else -> 0.0
}

/**
 * Deducciones por hijos, rangos
 */
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

/**
 * Deducciones por discapacidad por rangos efectivos.
 */
private fun minimoPorDiscapacidad(discapacidad: String): Double = when (discapacidad) {
    "33% - 64%" -> 3000.0
    "65% o más" -> 9000.0
    else -> 0.0
}