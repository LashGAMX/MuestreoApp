package com.acama.muestreoapp.models

class CatPhCalidad {
    var Id_ph : Int = 0
    var Ph_calidad : String = ""
    var Marca : String = ""
    var Lote : String = ""
    var Inicio : String = ""
    var Fin : String = ""

    constructor(Ph_calidad: String,Marca:String,Lote:String,Inicio:String,Fin:String){
        this.Ph_calidad = Ph_calidad
        this.Marca = Marca
        this.Lote = Lote
        this.Inicio = Inicio
        this.Fin = Fin
    }
}