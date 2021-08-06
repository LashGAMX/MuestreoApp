package com.acama.muestreoapp.models

class GastoMuestra {
    var Id_solicitud : Int = 0
    var Num_muestra : Int = 0
    var Gasto1 : String = ""
    var Gasto2 : String = ""
    var Gasto3 : String = ""
    var Promedio : String = ""

    constructor(Id_solicitud: Int,Num_muestra:Int,Gasto1:String,Gasto2:String,Gasto3:String,Promedio:String){
        this.Id_solicitud = Id_solicitud
        this.Num_muestra = Num_muestra
        this.Gasto1 = Gasto1
        this.Gasto2 = Gasto2
        this.Gasto3 = Gasto3
        this.Promedio = Promedio
    }
}