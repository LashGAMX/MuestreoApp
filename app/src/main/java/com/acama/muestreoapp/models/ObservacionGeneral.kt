package com.acama.muestreoapp.models

class ObservacionGeneral {

    var Observacion: String = ""
    var Folio: String = ""

    constructor(Observacion: String, Folio: String) {

        this.Folio = Folio
        this.Observacion = Observacion
    }
}