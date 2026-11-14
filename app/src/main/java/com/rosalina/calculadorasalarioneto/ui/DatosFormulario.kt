package com.rosalina.calculadorasalarioneto.ui

data class DatosFormulario(
    val salarioBruto: String = "",
    val edad: String = "",
    val hijos: String = "",
    val estadoCivil: String = "Soltero/a",
    val pagas: String = "12",
    val grupoProfesional: String = "1",
    val gradoDiscapacidad: String = "Sin discapacidad"
)