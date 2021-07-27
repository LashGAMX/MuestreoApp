package com.acama.muestreoapp.models

class CatPhTrazable {
    var Id_ph : Int = 0
    var Ph : String = ""
    var Marca : String = ""
    var Lote : String = ""
    var Inicio : String = ""
    var Fin : String = ""

    constructor(Ph: String,Marca:String,Lote:String,Inicio:String,Fin:String){
        this.Ph = Ph
        this.Marca = Marca
        this.Lote = Lote
        this.Inicio = Inicio
        this.Fin = Fin
    }
}