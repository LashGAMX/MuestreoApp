package com.acama.muestreoapp.models

class PhCalidadMuestra  {
    var Id_solicitud : Int = 0
    var Num_toma : Int = 0
    var Lectura1C : String = ""
    var Lectura2C : String = ""
    var Lectura3C : String = ""
    var Estado : String = ""
    var PromedioC : String = ""

    constructor(Id_solicitud: Int,Num_toma:Int,Lectura1C:String,
                Lectura2C:String,Lectura3C:String,Estado: String,
                PromedioC:String){
        this.Id_solicitud = Id_solicitud
        this.Num_toma = Num_toma
        this.Lectura1C = Lectura1C
        this.Lectura2C = Lectura2C
        this.Lectura3C = Lectura3C
        this.Estado = Estado
        this.PromedioC = PromedioC
    }
}