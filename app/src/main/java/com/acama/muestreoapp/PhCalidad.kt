package com.acama.muestreoapp

class PhCalidad {
    var Id_ph : Int = 0
    var Id_solicitudC : Int = 0
    var Id_phCalidadC : Int = 0
    var Lectura1C : Int = 0
    var Lectura2C : Int = 0
    var Lectura3C: Int = 0
    var EstadoC : String = ""
    var PromedioC : Int = 0

    constructor(Id_solicitudC: Int, Id_phCalidadC: Int, Lectura1C: Int, Lectura2C: Int, Lectura3C: Int,
                EstadoC: String, PromedioC: Int){
        this.Id_solicitudC = Id_solicitudC
        this.Id_phCalidadC = Id_phCalidadC
        this.Lectura1C = Lectura1C
        this.Lectura2C = Lectura2C
        this.Lectura3C = Lectura3C
        this.EstadoC = EstadoC
        this.PromedioC = PromedioC
    }
}