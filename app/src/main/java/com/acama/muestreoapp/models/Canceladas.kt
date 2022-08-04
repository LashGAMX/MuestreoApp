package com.acama.muestreoapp.models

class Canceladas {

    var Folio: String = ""
    var Estado: Int = 0
    var Muestra: Int = 0

    constructor(Folio: String, Muestra: Int, Estado: Int) {

        this.Folio = Folio
        this.Muestra = Muestra
        this.Estado = Estado
    }
}