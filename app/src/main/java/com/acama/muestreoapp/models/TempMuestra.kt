package com.acama.muestreoapp.models

class TempMuestra {
    var Id_solicitud : Int = 0
    var Num_muestra : Int = 0
    var Temp1 : String = ""
    var Temp2 : String = ""
    var Temp3 : String = ""
    var Promedio : String = ""

    constructor(Id_solicitud: Int,Num_muestra:Int,Temp1:String,Temp2:String,Temp3:String,Promedio:String){
        this.Id_solicitud = Id_solicitud
        this.Num_muestra = Num_muestra
        this.Temp1 = Temp1
        this.Temp2 = Temp2
        this.Temp3 = Temp3
        this.Promedio = Promedio
    }
}