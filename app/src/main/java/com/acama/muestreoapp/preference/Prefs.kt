package com.acama.muestreoapp.preference

import android.content.Context

class Prefs(val context:Context) {

    val SHARED_NAME = "MyCampo"
    val FIRST_START = "first"

    val storage = context.getSharedPreferences(SHARED_NAME,0)

    fun saveStart(first:Boolean){
        storage.edit().putBoolean(FIRST_START,first).apply()
    }

    fun getStart():Boolean{
        return storage.getBoolean(FIRST_START,false)
    }

}