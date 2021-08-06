package com.acama.muestreoapp.models

class ConMuestra {
    var Id_solicitud : Int = 0
    var Num_muestra : Int = 0
    var Con1 : String = ""
    var Con2 : String = ""
    var Con3 : String = ""
    var Promedio : String = ""

    constructor(Id_solicitud: Int,Num_muestra:Int,Con1:String,Con2:String,Con3:String,Promedio:String){
        this.Id_solicitud = Id_solicitud
        this.Num_muestra = Num_muestra
        this.Con1 = Con1
        this.Con2 = Con2
        this.Con3 = Con3
        this.Promedio = Promedio
    }
}