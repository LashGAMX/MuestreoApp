package com.acama.muestreoapp.models

class SolicitudGenerada {
    var Id_solicitudGen : Int = 0
    var Folio_servicio : String = ""
    var Id_solicitud : Int = 0
    var Id_intermediario : Int = 0
    var Nombres : String = ""
    var Id_cliente : Int = 0
    var Empresa : String = ""
    var Id_direccion : Int = 0
    var Id_contacto :Int = 0
    var Observacion : String = ""
    var Servicio : String = ""
    var Descarga : String = ""
    var Clave : String = ""
    var Fecha_muestreo : String = ""
    var Num_tomas :String = ""
    var Id_muestreador : Int = 0
    var Estado : Int = 0
    var Punto : String = ""

    constructor(Folio_servicio:String,Id_solicitud:Int
                ,Id_intermediario:Int,
                Nombres:String,Id_cliente:Int,Empresa:String,
                Id_direccion:Int,Id_contacto:Int,
                Observacion:String,Servicio:String,Descarga:String,
                Clave:String,Fecha_muestreo:String,Num_tomas:String,
                Id_muestreador:Int,Estado:Int,Punto:String){
        this.Folio_servicio = Folio_servicio
        this.Id_solicitud = Id_solicitud
        this.Id_intermediario = Id_intermediario
        this.Nombres = Nombres
        this.Id_cliente = Id_cliente
        this.Empresa = Empresa
        this.Id_direccion = Id_direccion
        this.Id_contacto = Id_contacto
        this.Observacion = Observacion
        this.Servicio = Servicio
        this.Descarga = Descarga
        this.Clave = Clave
        this.Fecha_muestreo = Fecha_muestreo
        this.Num_tomas = Num_tomas
        this.Id_muestreador = Id_muestreador
        this.Estado = Estado
        this.Punto = Punto
    }
    constructor()
}