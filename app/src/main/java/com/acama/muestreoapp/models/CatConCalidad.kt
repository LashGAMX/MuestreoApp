package com.acama.muestreoapp.models

class CatConCalidad {
    var Id_conductividad : Int = 0
    var Conductividad : String = ""
    var Marca : String = ""
    var Lote : String = ""
    var Inicio : String = ""
    var Fin : String = ""

    constructor(Conductividad: String,Marca:String,Lote:String,Inicio:String,Fin:String){
        this.Conductividad = Conductividad
        this.Marca = Marca
        this.Lote = Lote
        this.Inicio = Inicio
        this.Fin = Fin
    }
}