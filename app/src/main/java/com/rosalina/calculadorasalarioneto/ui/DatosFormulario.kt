package com.rosalina.calculadorasalarioneto.ui

/**
 * Datos principales para el calculo de la actividad1
 */
data class DatosFormulario(
    val salarioBruto: String = "",
    val edad: String = "",
    val hijos: String = "",
    val estadoCivil: String = "Soltero/a",
    val pagas: String = "12",
    val grupoProfesional: String = "Grupo 1",
    val gradoDiscapacidad: String = "Sin discapacidad"
)