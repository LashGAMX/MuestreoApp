package com.acama.muestreoapp.models

class CampoCompuesto {
    var Id_solicitud : Int = 0
    var Aforo: String = ""
    var ConTratamiento : String = ""
    var TipoTratamiento : String = ""
    var ProcMuestreo : String = ""
    var Observaciones : String = ""
    var ObserSolicitud : String = ""
    var PhMuestraCom : String = ""
    var Temp_muestraComp : String = ""
    var VolCalculado : String = ""
    var Cloruros : String = ""
    var Cloro : String = ""
    constructor(Id_solicitud:Int,Aforo:String,ConTratamiento:String,
                TipoTratamiento:String,ProcMuestreo:String,Observaciones:String,
                ObserSolicitud:String,PhMuestraCom:String,Temp_muestraComp:String,
                VolCalculado:String, Cloruros:String, Cloro:String){

        this.Id_solicitud = Id_solicitud
        this.Aforo = Aforo
        this.ConTratamiento = ConTratamiento
        this.TipoTratamiento = TipoTratamiento
        this.ProcMuestreo = ProcMuestreo
        this.Observaciones = Observaciones
        this.ObserSolicitud = ObserSolicitud
        this.PhMuestraCom = PhMuestraCom
        this.Temp_muestraComp = Temp_muestraComp
        this.VolCalculado = VolCalculado
        this.Cloruros = Cloruros
        this.Cloro = Cloro

    }
}