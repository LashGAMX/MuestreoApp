package com.acama.muestreoapp.models

class CampoCompuesto {
    var Id_solicitud : Int = 0
    var Aforo: String = ""
    var ConTratamiento : String = ""
    var TipoTratamiento : String = ""
    var  ProcMuestreo : String = ""
    var Observaciones : String = ""
    var ObserSolicitud : String = ""
    var PhMuestraCom : String = ""
    var VolCalculado : String =""

    constructor(Id_solicitud:Int,Aforo:String,Contratamiento:String,
                TipoTratamiento:String,ProcMuestreo:String,Observaciones:String,
                ObserSolicitud:String,PhMuestraCom:String,VolCalculado:String){

        this.Id_solicitud = Id_solicitud
        this.Aforo = Aforo
        this.ConTratamiento = ConTratamiento
        this.TipoTratamiento = TipoTratamiento
        this.ProcMuestreo = ProcMuestreo
        this.Observaciones = Observaciones
        this.ObserSolicitud = ObserSolicitud
        this.PhMuestraCom = PhMuestraCom
        this.VolCalculado = VolCalculado

    }
}