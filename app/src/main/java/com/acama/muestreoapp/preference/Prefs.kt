package com.acama.muestreoapp.preference

import android.content.Context

class Prefs(val context:Context) {

    val BASE_URL = "https://dev.sistemaacama.com.mx/api/app/"
    val SHARED_NAME = "MyCampo"
    val FIRST_START = "first"
    val MUESTREADORID = "0"
    val MUESTREADOR = "null"
    val PERCONECTADO = "NO"

    val storage = context.getSharedPreferences(SHARED_NAME,0)

    fun saveFirstStart(first:Boolean){
        storage.edit().putBoolean(FIRST_START,first).apply()
    }

    fun getFirstStart():Boolean{
        return storage.getBoolean(FIRST_START,false)
    }

    fun saveMuestreador(id:String,name:String){
        storage.edit().putString(MUESTREADORID,id).apply()
        storage.edit().putString(MUESTREADOR,name).apply()
    }
    fun getMuestreadorId():String{
        return storage.getString(MUESTREADORID,"")!!
    }
    fun getMuestreador():String{
        return storage.getString(MUESTREADOR,"")!!
    }
    fun savePermanecerConectado(sw:Boolean){
        storage.edit().putBoolean(PERCONECTADO,sw).apply()
    }
    fun getPermanecerConectado():Boolean{
        return storage.getBoolean(PERCONECTADO,false)!!
    }

}