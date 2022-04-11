package com.acama.muestreoapp.models

class ObservacionGeneral {

    var Observacion: String = ""
    var Folio: String = ""

    fun constructor(Observacion: String, Folio: String) {

        this.Folio = Folio
        this.Observacion = Observacion
    }
}