package com.acama.muestreoapp

class PhTrazable{
    var Id_ph : Int = 0
    var Id_solicitud : Int = 0
    var Id_phTrazable : Int = 0
    var Lectura1 : String = ""
    var Lectura2 : String = ""
    var Lectura3 : String = ""
    var Estado : String = ""

    constructor(Id_solicitud: Int, Id_phTrazable: Int, Lectura1: String, Lectura2: String, Lectura3: String,
    Estado: String){
        this.Id_solicitud = Id_solicitud
        this.Id_phTrazable = Id_phTrazable
        this.Lectura1 = Lectura1
        this.Lectura2 = Lectura2
        this.Lectura3 = Lectura3
        this.Estado = Estado
    }
}





