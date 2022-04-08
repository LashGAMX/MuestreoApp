package com.acama.muestreoapp.models

class Evidencia {
    var Id_evidencia : Int = 0
    var Folio : String = ""
    var Codigo : String = ""

    constructor(Id_evidencia: Int, Folio: String, Codigo: String) {
        this.Id_evidencia = Id_evidencia
        this.Folio = Folio
        this.Codigo = Codigo
    }

}