package com.acama.muestreoapp.models

class TempAmbiente {
    var Id_solicitud : Int = 0
    var Num_muestra : Int = 0
    var TempA1 : String = ""
    var TempA2 : String = ""
    var TempA3 : String = ""
    var PromedioA : String = ""

    constructor(Id_solicitud: Int,Num_muestra:Int,TempA1:String,TempA2:String,TempA3:String,PromedioA:String){
        this.Id_solicitud = Id_solicitud
        this.Num_muestra = Num_muestra
        this.TempA1 = TempA1
        this.TempA2 = TempA2
        this.TempA3 = TempA3
        this.PromedioA = PromedioA
    }
}