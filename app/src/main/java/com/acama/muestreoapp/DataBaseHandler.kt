package com.acama.muestreoapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.acama.muestreoapp.models.*


val DATABASE_NAME = "Muestreo"
val DATOSGENERALES = "campo_generales"
val Id_general = "Id_general"
val Id_solicitud = "Id_solicitud"
val Captura = "Captura"
val Id_equipo = "Id_equipo"
val Temperatura_a = "Temperatura_a"
val Temperatura_b = "Temperatura_b"
val Latitud = "Latitud"
val Longitud = "Longitud"
val Pendiente = "Pendiente"
val Criterio = "Criterio"

//ph trazbale
val PHTRAZABLE = "PhTrazable"
val Id_solicitudT = "Id_solicitud"
val Id_phTrazable = "Id_phTrazable"
val Lectura1 = "Lectura1"
val Lectura2 = "Lectura2"
val Lectura3 = "Lectura3"
val Estado = "Estado"

//ph calidad
val PHCALIDAD = "PhCalidad"
val Id_solicitudC = "Id_solicitud"
val Id_phCalidad = "Id_phCalidad"
val Lectura1C = "Lectura1"
val Lectura2C = "Lectura2"
val Lectura3C = "Lectura3"
val EstadoC = "Estado"
val PromedioC = "Promedio"

//Conductividad
val CONDUCTIVIDAD = "Conductividad"
val Id_Conductividad = "Id_conductividad"
val Id_solicitudCond = "Id_solicitud"
val Conductividad1 = "Conductividad1"
val Conductividad2 = "Conductividad2"
val Conductividad3 = "Conductividad3"
val PromedioCond = "Promedio"

//Usuarios app
val USUARIOS = "usuarios_app"
val Id_usuario = "Id_usuario"
val Id_muestreador = "Id_muestreador"
val User = "User"
val UserPass = "UserPass"

//Solicitud Generada
val SOLGENERADA = "solicitud_generadas"
val TERMOMETRO = "termometro_campo"
val CATPHTRAZABLE = "cat_phTrazable"
val CATPHCALIDAD = "cat_phCalidad"

class DataBaseHandler (var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
       createTableDatosGenerales(db)
        createTableUsuariosApp(db)
        createSolicitudGenerada(db)
        createTablePhTrazable(db)
        createTablePhCalidad(db)
        createCatPhTrazable(db)
        createCatPhCalidad(db)
        createTermometro(db)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
    fun createCatPhTrazable(db: SQLiteDatabase?){
        val model = "CREATE TABLE "+ CATPHTRAZABLE +" (" +
                "Id_ph INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Ph TEXT NOT NULL," +
                "Marca TEXT NOT NULL," +
                "Lote TEXT NOT NULL," +
                "Inicio_caducidad TEXT NOT NULL," +
                "Fin_caducidad TEXT NOT NULL," +
                ")"
        db?.execSQL(model)
    }
    fun insertPhTrazable(tra: CatPhTrazable) {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put("Ph",tra.Ph)
        cv.put("Marca", tra.Marca)
        cv.put("Lote", tra.Lote)
        cv.put("Inicio_caducidad", tra.Inicio)
        cv.put("Fin_caducidad", tra.Fin)
        var result = db.insert(CATPHTRAZABLE, null,cv)
        if( result == -1.toLong())
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
    fun insertPhCalidad(tra: CatPhCalidad) {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put("Ph_calidad",tra.Ph_calidad)
        cv.put("Marca", tra.Marca)
        cv.put("Lote", tra.Lote)
        cv.put("Inicio_caducidad", tra.Inicio)
        cv.put("Fin_caducidad", tra.Fin)
        var result = db.insert(CATPHTRAZABLE, null,cv)
        if( result == -1.toLong())
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
    fun createCatPhCalidad(db: SQLiteDatabase?){
        val model = "CREATE TABLE "+ CATPHCALIDAD +" (" +
                "Id_ph INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Ph_clidad TEXT NOT NULL," +
                "Marca TEXT NOT NULL," +
                "Lote TEXT NOT NULL," +
                "Inicio_caducidad TEXT NOT NULL," +
                "Fin_caducidad TEXT NOT NULL," +
                ")"
        db?.execSQL(model)
    }
    // Inicio Usuario
    fun createTableUsuariosApp(db: SQLiteDatabase?)
    {
        val usuarios_app = "CREATE TABLE " + USUARIOS + " (" +
                Id_usuario + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Id_muestreador + " INTEGER," +
                User + " VARCHAR(100)," +
                UserPass + " VARCHAR(100))"


        db?.execSQL(usuarios_app)
    }
    fun inserUsuario(usuarios: Usuarios) {
        val db = this.writableDatabase
        var cv = ContentValues()
        //put datos
        cv.put(Id_muestreador,usuarios.Id_muestreador)
        cv.put(User,usuarios.User)
        cv.put(UserPass,usuarios.UserPass)

        var result = db.insert(USUARIOS, null,cv)
        if( result == -1.toLong())
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
    // Fin Usuario
    // Inicio SolicitudGenerada
    fun createSolicitudGenerada(db: SQLiteDatabase?){
        val solicitud = "CREATE TABLE "+ SOLGENERADA +" (" +
                "Id_solicitudGen INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Folio_servicio VARCHAR(100) NOT NULL," +
                "Id_solicitud INTEGER NOT NULL," +
                "Id_intermediario INTEGER NOT NULL," +
                "Nombres VARCHAR(255) NOT NULL," +
                "Id_cliente INTEGER NOT NULL," +
                "Empresa VARCHAR(255) NOT NULL," +
                "Direccion VARCHAR(255) NOT NULL," +
                "Contacto VARCHAR(255) NOT NULL," +
                "Observacion VARCHAR(255)," +
                "Servicio VARCHAR(255)," +
                "Descarga VARCHAR(255)," +
                "Clave VARCHAR(255)," +
                "Fecha_muestreo ARCHAR(255)," +
                "Num_tomas INTEGER," +
                "Id_muestreador INTEGER," +
                "Estado INTEGER" +
                ")"
        db?.execSQL(solicitud)
    }
    fun insertSolicitudGenerada(sol: SolicitudGenerada) {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put("Folio_servicio",sol.Folio_servicio)
        cv.put("Id_solicitud", sol.Id_solicitud)
        cv.put("Id_intermediario", sol.Id_intermediario)
        cv.put("Nombres", sol.Nombres)
        cv.put("Id_cliente", sol.Id_cliente)
        cv.put("Empresa", sol.Empresa)
        cv.put("Direccion", sol.Direccion)
        cv.put("Contacto", sol.Contacto)
        cv.put("Observacion", sol.Observacion)
        cv.put("Servicio", sol.Servicio)
        cv.put("Descarga", sol.Descarga)
        cv.put("Clave", sol.Clave)
        cv.put("Fecha_muestreo", sol.Fecha_muestreo)
        cv.put("Num_tomas", sol.Num_tomas)
        cv.put("Id_muestreador", sol.Id_muestreador)
        cv.put("Estado", sol.Estado)

        var result = db.insert(SOLGENERADA, null,cv)

    }
    // Fin SolicitudGenerada
    // Inicio Termometro Campo
    fun createTermometro(db: SQLiteDatabase?){
        val model = "CREATE TABLE "+ TERMOMETRO +" (" +
                "Id_termometro INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Id_muestreador INTEGER NOT NULL," +
                "Equipo TEXT NOT NULL," +
                "Marca TEXT NOT NULL," +
                "Modelo TEXT NOT NULL," +
                "Serie TEXT NOT NULL," +
                ")"
        db?.execSQL(model)
    }
    fun insertTermometroCampo(term: TermometroCampo) {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put("Id_muestreador",term.Id_muestreador)
        cv.put("Equipo", term.Equipo)
        cv.put("Marca", term.Marca)
        cv.put("Modelo", term.Modelo)
        cv.put("Serie", term.Serie)
        var result = db.insert(TERMOMETRO, null,cv)
        if( result == -1.toLong())
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }

    }
    // Fin Termomentro Campo
    fun createTableDatosGenerales(db: SQLiteDatabase?)
    {
        val campo_general = "CREATE TABLE " + DATOSGENERALES + " (" +
                Id_general + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Id_solicitud + " INTEGER," +
                Captura + " VARCHAR(256)," +
                Id_equipo + " INTEGER," +
                Temperatura_a + " VARCHAR(256)," +
                Temperatura_b + " VARCHAR(256)," +
                Latitud + " VARCHAR(256)," +
                Longitud + " VARCHAR(256)," +
                Pendiente + " VARCHAR(256)," +
                Criterio + " VARCHAR(256))"
        db?.execSQL(campo_general)
    }

    fun insertGeneral(generales: Generales) {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(Id_solicitud,generales.Id_solicitud)
        cv.put(Captura, generales.Captura)
        cv.put(Id_equipo, generales.Id_equipo)
        cv.put(Temperatura_a, generales.Temperatura_a)
        cv.put(Temperatura_b, generales.Temperatura_b)
        cv.put(Latitud, generales.Latitud)
        cv.put(Longitud, generales.Longitud)
        cv.put(Pendiente, generales.Pendiente)
        cv.put(Criterio, generales.Criterio)
        var result = db.insert(DATOSGENERALES, null,cv)
        if( result == -1.toLong())
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
    fun createTablePhTrazable(db: SQLiteDatabase?){
        val phTrazable = "CREATE TABLE " + PHTRAZABLE + " (" +
                Id_phTrazable + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Id_solicitudT + " INTEGER," +
                Lectura1 + " VARCHAR(100)," +
                Lectura2 + " VARCHAR(100)," +
                Lectura3 + " VARCHAR(100)," +
                Estado + " VARCHAR(100))"


        db?.execSQL(phTrazable)
    }
    fun insertPhTrazable(phTrazable: PhTrazable) {
        val db = this.writableDatabase
        var cv = ContentValues()
        //put datos
        cv.put(Id_solicitudT,phTrazable.Id_solicitud)
        cv.put(Id_phTrazable, phTrazable.Id_phTrazable)
        cv.put(Lectura1, phTrazable.Lectura1)
        cv.put(Lectura2, phTrazable.Lectura2)
        cv.put(Lectura3, phTrazable.Lectura3)
        cv.put(Estado, phTrazable.Estado)

        var result = db.insert(PHTRAZABLE, null,cv)
        if( result == -1.toLong())
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    fun createTablePhCalidad(db: SQLiteDatabase?){
        val phCalidad = "CREATE TABLE " + PHCALIDAD + " (" +
                Id_phCalidad + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Id_solicitudC + " INTEGER," +
                Lectura1C + " VARCHAR(100)," +
                Lectura2C + " VARCHAR(100)," +
                Lectura3C + " VARCHAR(100)," +
                EstadoC + " VARCHAR(100)," +
                PromedioC + " VARCHAR(100))"


        db?.execSQL(phCalidad)
    }
    fun insertPhCalidad(phCalidad: PhCalidad) {
        val db = this.writableDatabase
        var cv = ContentValues()
        //put datos
        //cv.put(Id_solicitudC,phCalidad.Id_solicitudC)
        cv.put(Id_solicitudC, phCalidad.Id_phCalidadC)
        cv.put(Lectura1C, phCalidad.Lectura1C)
        cv.put(Lectura2C, phCalidad.Lectura2C)
        cv.put(Lectura3C, phCalidad.Lectura3C)
        cv.put(EstadoC, phCalidad.EstadoC)

        var result = db.insert(PHCALIDAD, null,cv)
        if( result == -1.toLong())
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
    fun createTableConductividad(db: SQLiteDatabase?){
        val conductividad = "CREATE TABLE " + CONDUCTIVIDAD + " (" +
                Id_Conductividad + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Id_solicitudCond + " INTEGER," +
                Conductividad1 + " VARCHAR(100)," +
                Conductividad2 + " VARCHAR(100)," +
                Conductividad3 + " VARCHAR(100)," +
                PromedioCond + " VARCHAR(100))"


        db?.execSQL(conductividad)
    }
    fun insertConductividad(conductividad: Conductividad) {
        val db = this.writableDatabase
        var cv = ContentValues()
        //put datos
        cv.put(Id_solicitudCond, conductividad.Id_conductividad)
        cv.put(Conductividad1, conductividad.Conductividad1)
        cv.put(Conductividad2, conductividad.Conductividad2)
        cv.put(Conductividad3, conductividad.Conductividad3)
        cv.put(PromedioCond, conductividad.Promedio)

        var result = db.insert(CONDUCTIVIDAD, null,cv)
        if( result == -1.toLong())
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteData(db: SQLiteDatabase?,sql:String) {
        db!!.execSQL(sql)
    }

}
