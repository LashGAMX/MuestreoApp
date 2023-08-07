package com.acama.muestreoapp.models

class TermometroCampo2 {
        var Id_termometro : Int = 0
        var Id_muestreador: Int = 0
        var Equipo : String = ""
        var Marca : String = ""
        var Modelo : String = ""
        var Serie : String = ""

        constructor(Id_termometro:Int,Id_muestreador: Int,Equipo: String,Marca:String,Modelo:String,Serie:String){
           this.Id_termometro = Id_termometro
            this.Id_muestreador = Id_muestreador
            this.Equipo = Equipo
            this.Marca = Marca
            this.Modelo = Modelo
            this.Serie = Serie
        }
}