package com.acama.muestreoapp

class Generales {
    var Id_general: Int = 0
    var Id_solicitud: Int = 0
    var Captura: String = ""
    var Id_equipo: Int = 0
    var Temperatura_a: String = ""
    var Temperatura_b: String = ""
    var Latitud: String = ""
    var Longitud: String = ""
    var Pendiente: String = ""
    var Criterio: String = ""

    constructor(Id_solicitud: Int, Captura: String, Id_equipo: Int, Temperatura_a: String,
                Temperatura_b: String, Latitud: String, Longitud: String, Pendiente: String,
                Criterio: String){


        this.Id_solicitud = Id_solicitud
        this.Captura = Captura
        this.Id_equipo = Id_equipo
        this.Temperatura_a = Temperatura_a
        this.Temperatura_b = Temperatura_b
        this.Latitud = Latitud
        this.Longitud = Longitud
        this.Pendiente = Pendiente
        this.Criterio = Criterio
    }
}