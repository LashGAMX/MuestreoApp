package com.acama.muestreoapp.models

class Canceladas {

    var Folio: String = ""
    var Id_solicitud: Int = 0
    var Estado: Int = 0
    var Muestra: Int = 0

    constructor(Folio: String, Id_solicitud: Int, Muestra: Int, Estado: Int) {

        this.Folio = Folio
        this.Id_solicitud = Id_solicitud
        this.Muestra = Muestra
        this.Estado = Estado
    }
}