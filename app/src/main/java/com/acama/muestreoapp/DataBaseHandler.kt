package com.acama.muestreoapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
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

//Usuarios app
val USUARIOS = "usuarios_app"
val Id_usuario = "Id_usuario"
val Id_muestreador = "Id_muestreador"
val User = "User"
val UserPass = "UserPass"

//Solicitud Generada
val PHCALIDAD = "ph_calidad"
val PHTRAZABLE = "ph_trazable"
val CONTRAZABLE = "con_trazable"
val CONCALIDAD = "con_calidad"
val SOLGENERADA = "solicitud_generadas"
val TERMOMETRO = "TermometroCampo"
val CATPHTRAZABLE = "cat_phTrazable"
val CATPHCALIDAD = "cat_phCalidad"
val CATCONTRAZABLE = "cat_conTrazable"
val CATCONCALIDAD = "cat_conCalidad"

val PHMUESTRA = "ph_muestra"
val TEMPMUESTRA = "temperatura_muestra"
val CONMUESTRA = "conductividad_muestra"
val GASTOMUESTRA = "gasto_muestra"

class DataBaseHandler (var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
       createTableDatosGenerales(db)
        createTableUsuariosApp(db)
        createSolicitudGenerada(db)
        createPhTrazable(db)
        createTablePhCalidad(db)
        createCatPhTrazable(db)
        createCatPhCalidad(db)
        createTermometro(db)
        createConTrazable(db)
        createConCalidad(db)
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
                "Fin_caducidad TEXT NOT NULL" +
                ")"
        db?.execSQL(model)
    }

    fun createCatPhCalidad(db: SQLiteDatabase?){
        val model = "CREATE TABLE "+ CATPHCALIDAD +" (" +
                "Id_ph INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Ph_calidad TEXT NOT NULL," +
                "Marca TEXT NOT NULL," +
                "Lote TEXT NOT NULL," +
                "Inicio_caducidad TEXT NOT NULL," +
                "Fin_caducidad TEXT NOT NULL" +
                ")"
        db?.execSQL(model)
    }
    fun createCatConTrazable(db: SQLiteDatabase?){
        val model = "CREATE TABLE "+ CATCONTRAZABLE +" (" +
                "Id_conductividad INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Conductividad TEXT NOT NULL," +
                "Marca TEXT NOT NULL," +
                "Lote TEXT NOT NULL," +
                "Inicio_caducidad TEXT NOT NULL," +
                "Fin_caducidad TEXT NOT NULL" +
                ")"
        db?.execSQL(model)
    }
    fun createCatConCalidad(db: SQLiteDatabase?){
        val model = "CREATE TABLE "+ CATCONCALIDAD +" (" +
                "Id_conductividad INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Conductividad TEXT NOT NULL," +
                "Marca TEXT NOT NULL," +
                "Lote TEXT NOT NULL," +
                "Inicio_caducidad TEXT NOT NULL," +
                "Fin_caducidad TEXT NOT NULL" +
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

    fun insertCatPhTrazable(ph: CatPhTrazable) {
        val db = this.writableDatabase
        var cv = ContentValues()
        //put datos
        cv.put("Ph",ph.Ph)
        cv.put("Marca",ph.Marca)
        cv.put("Lote",ph.Lote)
        cv.put("Inicio_caducidad",ph.Inicio)
        cv.put("Fin_caducidad",ph.Fin)

        var result = db.insert(CATPHTRAZABLE, null,cv)
        if( result == -1.toLong())
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
    fun insertCatPhCalidad(ph: CatPhCalidad) {
        val db = this.writableDatabase
        var cv = ContentValues()
        //put datos
        cv.put("Ph_calidad",ph.Ph_calidad)
        cv.put("Marca",ph.Marca)
        cv.put("Lote",ph.Lote)
        cv.put("Inicio_caducidad",ph.Inicio)
        cv.put("Fin_caducidad",ph.Fin)

        var result = db.insert(CATPHCALIDAD, null,cv)
        if( result == -1.toLong())
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
    fun insertCaTConTrazable(con: CatConTrazable) {
        val db = this.writableDatabase
        var cv = ContentValues()
        //put datos
        cv.put("Conductividad",con.Conductividad)
        cv.put("Marca",con.Marca)
        cv.put("Lote",con.Lote)
        cv.put("Inicio_caducidad",con.Inicio)
        cv.put("Fin_caducidad",con.Fin)

        var result = db.insert(CATCONTRAZABLE, null,cv)
        if( result == -1.toLong())
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
    fun insertCaTConCalidad(con: CatConCalidad) {
        val db = this.writableDatabase
        var cv = ContentValues()
        //put datos
        cv.put("Conductividad",con.Conductividad)
        cv.put("Marca",con.Marca)
        cv.put("Lote",con.Lote)
        cv.put("Inicio_caducidad",con.Inicio)
        cv.put("Fin_caducidad",con.Fin)

        var result = db.insert(CATCONCALIDAD, null,cv)
        if( result == -1.toLong())
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
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
                "Serie TEXT NOT NULL" +
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
    fun createPhTrazable(db: SQLiteDatabase?){
        val model = "CREATE TABLE " + PHTRAZABLE + " (" +
                "Id_ph INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Id_solicitud INTEGER," +
                "Id_phTrazable INTEGER," +
                "Lectura1 TEXT," +
                "Lectura2 TEXT," +
                "Lectura3 TEXT," +
                "Estado TEXT" +
                ")"
        db?.execSQL(model)
    }
    fun insertPhTrazable(phTrazable: PhTrazable) {
        val db = this.writableDatabase
        var cv = ContentValues()
        //put datos
        cv.put("Id_solicitud",phTrazable.Id_solicitud)
        cv.put("Id_phTrazable", phTrazable.Id_phTrazable)
        cv.put("Lectura1", phTrazable.Lectura1)
        cv.put("Lectura2", phTrazable.Lectura2)
        cv.put("Lectura3", phTrazable.Lectura3)
        cv.put("Estado", phTrazable.Estado)

        var result = db.insert(PHTRAZABLE, null,cv)
        if( result == -1.toLong())
        {
            //Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            Log.d("phTrazable","Correcto")
        }
        else
        {
            //Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            Log.d("phTrazable","Fallido")
        }
    }

    fun createTablePhCalidad(db: SQLiteDatabase?){
        val phCalidad = "CREATE TABLE " + PHCALIDAD + " (" +
                "Id_ph INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Id_solicitud INTEGER," +
                "Id_phCalidad INTEGER," +
                "Lectura1 VARCHAR(100)," +
                "Lectura2 VARCHAR(100)," +
                "Lectura3 VARCHAR(100)," +
                "Estado VARCHAR(100)," +
                "Promedio VARCHAR(100))"
        db?.execSQL(phCalidad)
    }
    fun insertPhCalidad(phCalidad: PhCalidad) {
        val db = this.writableDatabase
        var cv = ContentValues()
        //put datos
        cv.put("Id_solicitud",phCalidad.Id_solicitudC)
        cv.put("Id_phCalidad", phCalidad.Id_phCalidadC)
        cv.put("Lectura1", phCalidad.Lectura1C)
        cv.put("Lectura2", phCalidad.Lectura2C)
        cv.put("Lectura3", phCalidad.Lectura3C)
        cv.put("Estado", phCalidad.EstadoC)
        cv.put("Promedio", phCalidad.PromedioC)

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
    fun createConTrazable(db: SQLiteDatabase?){
        val conductividad = "CREATE TABLE " + CONTRAZABLE + " (" +
                "Id_conductividad INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Id_solicitud INTEGER," +
                "Id_conTrazable INTEGER," +
                "Lectura1 VARCHAR(100)," +
                "Lectura2 VARCHAR(100)," +
                "Lectura3 VARCHAR(100)," +
                "Estado VARCHAR(100))"


        db?.execSQL(conductividad)
    }
    fun insertConTrazable(conductividad: ConTrazable) {
        val db = this.writableDatabase
        var cv = ContentValues()
        //put datos
        cv.put("Id_solicitud", conductividad.Id_solicitud)
        cv.put("Id_conTrazable", conductividad.Id_conTrazable)
        cv.put("Lectura1", conductividad.Lectura1)
        cv.put("Lectura2", conductividad.Lectura2)
        cv.put("Lectura3", conductividad.Lectura3)
        cv.put("Estado", conductividad.Estado)

        var result = db.insert(CONTRAZABLE, null,cv)
        if( result == -1.toLong())
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
    fun createConCalidad(db: SQLiteDatabase?){
        val conductividad = "CREATE TABLE " + CONCALIDAD + " (" +
                "Id_conductividad INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Id_solicitud INTEGER," +
                "Id_conCalidad INTEGER," +
                "Lectura1 VARCHAR(100)," +
                "Lectura2 VARCHAR(100)," +
                "Lectura3 VARCHAR(100)," +
                "Estado VARCHAR(100)," +
                "Promedio VARCHAR(100)" +
                ")"


        db?.execSQL(conductividad)
    }
    fun insertConCalidad(conductividad: ConCalidad) {
        val db = this.writableDatabase
        var cv = ContentValues()
        //put datos
        cv.put("Id_solicitud", conductividad.Id_solicitud)
        cv.put("Id_conCalidad", conductividad.Id_conCalidad)
        cv.put("Lectura1", conductividad.Lectura1)
        cv.put("Lectura2", conductividad.Lectura2)
        cv.put("Lectura3", conductividad.Lectura3)
        cv.put("Estado", conductividad.Estado)
        cv.put("Promedio", conductividad.Promedio)

        var result = db.insert(CONCALIDAD, null,cv)
        if( result == -1.toLong())
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
    fun createPhMuestra(db: SQLiteDatabase?){
        val ph = "CREATE TABLE " + PHMUESTRA + " (" +
                "Id_ph INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Id_solicitud INTEGER," +
                "Materia VARCHAR(100)," +
                "Olor VARCHAR(100)," +
                "Color VARCHAR(100)," +
                "Ph1 FLOAT," +
                "Ph2 FLOAT," +
                "Ph3 FLOAT," +
                "Promedio FLOAT," +
                "Fecha VARCHAR(100)" +
                ")"
        db?.execSQL(ph)
    }
    fun insertPhMuestra(phMuestra: PhMuestra) {
        val db = this.writableDatabase
        var cv = ContentValues()
        //put datos
        cv.put("Id_solicitud", phMuestra.Id_solicitud)
        cv.put("Materia", phMuestra.Materia)
        cv.put("Olor", phMuestra.Olor)
        cv.put("Color", phMuestra.Color)
        cv.put("Ph1", phMuestra.Ph1)
        cv.put("Ph2", phMuestra.Ph2)
        cv.put("Ph3", phMuestra.Ph3)
        cv.put("Promedio", phMuestra.Promedio)
        cv.put("Fecha", phMuestra.Fecha)

        var result = db.insert(PHMUESTRA, null,cv)
        if( result == -1.toLong())
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
    fun createConMuestra(db: SQLiteDatabase?){
        val conduc = "CREATE TABLE " + CONMUESTRA + " (" +
                "Id_conductividad INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Id_solicitud INTEGER," +
                "Conductividad1 FLOAT," +
                "Conductividad2 FLOAT," +
                "Conductividad3 FLOAT," +
                "Promedio FLOAT," +
                ")"
        db?.execSQL(conduc)
    }
    fun insertConMuestra(conMuestra: ConMuestra) {
        val db = this.writableDatabase
        var cv = ContentValues()
        //put datos
        cv.put("Id_solicitud", conMuestra.Id_solicitud)
        cv.put("Conductividad1", conMuestra.Con1)
        cv.put("Conductividad2", conMuestra.Con2)
        cv.put("Conductividad3", conMuestra.Con3)
        cv.put("Promedio", conMuestra.Promedio)

        var result = db.insert(CONMUESTRA, null,cv)
        if( result == -1.toLong())
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
    fun createTempMuestra(db: SQLiteDatabase?){
        val ph = "CREATE TABLE " + TEMPMUESTRA + " (" +
                "Id_temperatura INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Id_solicitud INTEGER," +
                "Temp1 FLOAT," +
                "Temp2 FLOAT," +
                "Temp3 FLOAT," +
                "Promedio FLOAT," +
                ")"
        db?.execSQL(ph)
    }
    fun insertTempMuestra(tempMuestra: TempMuestra) {
        val db = this.writableDatabase
        var cv = ContentValues()
        //put datos
        cv.put("Id_solicitud", tempMuestra.Id_solicitud)
        cv.put("Temp1", tempMuestra.Temp1)
        cv.put("Temp2", tempMuestra.Temp2)
        cv.put("Temp3", tempMuestra.Temp3)
        cv.put("Promedio", tempMuestra.Promedio)

        var result = db.insert(TEMPMUESTRA, null,cv)
        if( result == -1.toLong())
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
    fun createGastoMuestra(db: SQLiteDatabase?){
        val ph = "CREATE TABLE " + GASTOMUESTRA + " (" +
                "Id_gasto INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Id_solicitud INTEGER," +
                "Gasto1 FLOAT," +
                "Gasto2 FLOAT," +
                "Gasto3 FLOAT," +
                "Promedio FLOAT," +
                ")"
        db?.execSQL(ph)
    }
    fun insertGastoMuestra(gastoMuestra: GastoMuestra) {
        val db = this.writableDatabase
        var cv = ContentValues()
        //put datos
        cv.put("Id_solicitud", gastoMuestra.Id_solicitud)
        cv.put("Gasto1", gastoMuestra.Gasto1)
        cv.put("Gasto2", gastoMuestra.Gasto2)
        cv.put("Gasto3", gastoMuestra.Gasto3)
        cv.put("Promedio", gastoMuestra.Promedio)

        var result = db.insert(GASTOMUESTRA, null,cv)
        if( result == -1.toLong())
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
}
