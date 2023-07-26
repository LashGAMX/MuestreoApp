package com.acama.muestreoapp.models

class PhCalidad {
    var Id_solicitud: Int = 0
    var Id_phCalidad: Int = 0
    var Num_muestra: Int = 0
    var Lectura1: String = ""
    var Lectura2: String = ""
    var Lectura3: String = ""
    var Estado: String = ""
    var Promedio: String = ""

    constructor(
        Id_solicitud: Int,
        Id_phCalidad: Int,
        Num_muestra: Int,
        Lectura1: String,
        Lectura2: String,
        Lectura3: String,
        Estado: String,
        Promedio: String
    ) {
        this.Id_solicitud = Id_solicitud
        this.Id_phCalidad = Id_phCalidad
        this.Num_muestra = Num_muestra
        this.Lectura1 = Lectura1
        this.Lectura2 = Lectura2
        this.Lectura3 = Lectura3
        this.Estado = Estado
        this.Promedio = Promedio
    }
}