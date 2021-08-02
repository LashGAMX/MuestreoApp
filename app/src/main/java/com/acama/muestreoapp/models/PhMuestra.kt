package com.acama.muestreoapp.models

class PhMuestra {

    var Id_solicitud : Int = 0
    var Materia : String = ""
    var Olor : String = ""
    var Color : String = ""
    var Ph1 : String = ""
    var Ph2 : String = ""
    var Ph3 : String = ""
    var Promedio : String = ""
    var Fecha : String = ""

    constructor(Id_solicitud: Int,Materia:String,Olor:String,Color:String,Ph1:String,Ph2:String,Ph3:String,Promedio:String,Fecha:String){
        this.Id_solicitud = Id_solicitud
        this.Materia = Materia
        this.Olor = Olor
        this.Color = Color
        this.Ph1 = Ph1
        this.Ph2 = Ph2
        this.Ph3 = Ph3
        this.Promedio = Promedio
        this.Fecha = Fecha
    }
}