package com.rosalina.calculadorasalarioneto.ui

data class ResultadoSalario(
    val salarioBruto: Double,
    val cotizacionSS: Double,
    val retencionIrpf: Double,
    val deducciones: Double,
    val salarioNeto: Double,
    val netoPorPaga: Double,
)
fun calcularSalario(datos: DatosFormulario): ResultadoSalario? {
    val bruto = datos.salarioBruto.toDoubleOrNull() ?: return null
    val pagas = datos.pagas.toInt()

    val cotizacionSS = bruto * 0.0648
    val retencionIrpf = 0.0      // lo añadimos en el siguiente paso
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