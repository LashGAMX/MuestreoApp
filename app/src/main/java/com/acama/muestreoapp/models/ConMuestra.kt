package com.acama.muestreoapp.models

class ConMuestra {
    var Id_solicitud : Int = 0
    var Con1 : String = ""
    var Con2 : String = ""
    var Con3 : String = ""
    var Promedio : String = ""

    constructor(Id_solicitud: Int,Con1:String,Con2:String,Con3:String,Promedio:String){
        this.Id_solicitud = Id_solicitud
        this.Con1 = Con1
        this.Con2 = Con2
        this.Con3 = Con3
        this.Promedio = Promedio
    }
}