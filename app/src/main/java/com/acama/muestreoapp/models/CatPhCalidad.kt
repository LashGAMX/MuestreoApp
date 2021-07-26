package com.acama.muestreoapp.models

class CatPhCalidad {
    var Id_ph : Int = 0
    var Ph_calidad : String = ""
    var Marca : String = ""
    var Lote : String = ""
    var Inicio : String = ""
    var Fin : String = ""

    constructor(Id_ph: Int,Ph_calidad: String,Marca:String,Lote:String,Inicio:String){
        this.Id_ph = Id_ph
        this.Ph_calidad = Ph_calidad
        this.Marca = Marca
        this.Lote = Lote
        this.Inicio = Inicio
        this.Fin = Fin
    }
}