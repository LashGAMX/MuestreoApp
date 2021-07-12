package com.acama.muestreoapp

class PhCalidad {
    var Id_ph : Int = 0
    var Id_solicitudC : Int = 0
    var Id_phCalidad : Int = 0
    var Lectura1 : Int = 0
    var Lectura2 : Int = 0 
    var Lectura3 : Int = 0
    var Estado : String = ""
    var Promedio : Int = 0

    constructor(Id_solicitudC: Int, Id_phCalidad: Int, Lectura1: Int, Lectura2: Int, Lectura3: Int,
                Estado: String, Promedio: Int){
        this.Id_solicitudC = Id_solicitudC
        this.Id_phCalidad = Id_phCalidad
        this.Lectura1 = Lectura1
        this.Lectura2 = Lectura2
        this.Lectura3 = Lectura3
        this.Estado = Estado
        this.Promedio = Promedio
    }
}