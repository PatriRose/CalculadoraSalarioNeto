package com.rosalina.calculadorasalarioneto.ui

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
    val retencionIrpf = cuotaPorTramos(baseIrpf)
    val deducciones = 0.0

    val neto = bruto - cotizacionSS - retencionIrpf
    return ResultadoSalario(
        bruto,
        cotizacionSS,
        retencionIrpf,
        deducciones,
        neto,
        neto / pagas)
}

fun formatoEuros ( valor: Double): String ="%.2f €".format(valor)

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