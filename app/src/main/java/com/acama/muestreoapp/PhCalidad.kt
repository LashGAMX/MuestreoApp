package com.acama.muestreoapp

class PhCalidad {
    var Id_ph : Int = 0
    var Id_solicitudC : Int = 0
    var Id_phCalidadC : Int = 0
    var Lectura1C : String = ""
    var Lectura2C : String = ""
    var Lectura3C: String = ""
    var EstadoC : String = ""
    var PromedioC : String = ""

    constructor(Id_solicitudC: Int, Id_phCalidadC: Int, Lectura1C: String, Lectura2C: String, Lectura3C: String,
                EstadoC: String, PromedioC: String){
        this.Id_solicitudC = Id_solicitudC
        this.Id_phCalidadC = Id_phCalidadC
        this.Lectura1C = Lectura1C
        this.Lectura2C = Lectura2C
        this.Lectura3C = Lectura3C
        this.EstadoC = EstadoC
        this.PromedioC = PromedioC
    }
}