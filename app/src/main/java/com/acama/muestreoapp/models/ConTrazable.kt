package com.acama.muestreoapp.models

class ConTrazable {

    var Id_solicitud : Int = 0
    var Id_conTrazable : Int = 0
    var Lectura1 : String = ""
    var Lectura2 : String = ""
    var Lectura3 : String = ""
    var Estado : String = ""

    constructor(Id_solicitud: Int,Id_conTrazable:Int,Lectura1:String,Lectura2:String,Lectura3:String,Estado:String){
        this.Id_solicitud = Id_solicitud
        this.Id_conTrazable = Id_conTrazable
        this.Lectura1 = Lectura1
        this.Lectura2 = Lectura2
        this.Lectura3 = Lectura3
        this.Estado = Estado
    }
}