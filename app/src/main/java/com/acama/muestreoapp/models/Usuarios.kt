package com.acama.muestreoapp.models

class Usuarios {
    var Id_usuario : Int = 0
    var Id_muestreador : Int = 0
    var User : String = ""
    var UserPass : String = ""

    constructor(Id_muestreador: Int,User:String,UserPass:String){
        this.Id_muestreador = Id_muestreador
        this.User = User
        this.UserPass = UserPass
    }
}