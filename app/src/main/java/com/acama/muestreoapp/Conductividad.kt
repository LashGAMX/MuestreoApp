package com.acama.muestreoapp

class Conductividad {
    var Id_conductividad : Int = 0
    var Id_solicitud : Int = 0
    var Conductividad1 : String = ""
    var Conductividad2 : String = ""
    var Conductividad3 : String = ""
    var Promedio : String = ""


    constructor(Id_solicitudCond:Int, Conductividad1:String, Conductividad2:String, Conductividad3:String,
    PromedioCond:String){

        this.Id_solicitud = Id_solicitudCond
        this.Conductividad1 = Conductividad1
        this.Conductividad2 = Conductividad2
        this.Conductividad3 = Conductividad3
        this.Promedio = PromedioCond
    }
}

