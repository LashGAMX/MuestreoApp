package com.acama.muestreoapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.strictmode.SqliteObjectLeakedViolation
import android.util.Log
import android.widget.Toast
import androidx.core.content.contentValuesOf
import com.acama.muestreoapp.models.*


val DATABASE_NAME = "Muestreo"
val DATOSGENERALES = "campo_generales"
val Id_general = "Id_general"
val Id_solicitud = "Id_solicitud"
val Captura = "Captura"
val Id_equipo = "Id_equipo"
val Id_equipo2 = "Id_equipo2"
val Temperatura_a = "Temperatura_a"
val Temperatura_b = "Temperatura_b"
val Latitud = "Latitud"
val Longitud = "Longitud"
val Pendiente = "Pendiente"
val Criterio = "Criterio"
val Supervisor = "Supervisor"

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
val TERMOMETRO2 = "TermometroCampo2"
val CATPHTRAZABLE = "cat_phTrazable"
val CATPHCALIDAD = "cat_phCalidad"
val CATCONTRAZABLE = "cat_conTrazable"
val CATCONCALIDAD = "cat_conCalidad"

val PHMUESTRA = "ph_muestra"
val TEMPMUESTRA = "temperatura_muestra"
val PHCALIDADMUESTRA = "ph_calidadMuestra"
val TEMPAMBIENTE = "temperatura_ambiente"
val CONMUESTRA = "conductividad_muestra"
val GASTOMUESTRA = "gasto_muestra"
val COLOR = "color"
val AFORO = "aforo"
val CONTRATAMIENTO = "con_tratamiento"
val TIPOTRATAMIENTO = "tipo_tratamiento"

val CAMPOCOMPUESTO = "campo_compuesto"

val OBSGENERAL = "observacion_general"

val EVIDENCIA = "evidencia"
val CANCELADAS = "canceladas"

class DataBaseHandler (var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 2){
    override fun onCreate(db: SQLiteDatabase?) {
       createTableDatosGenerales(db)
        createTableUsuariosApp(db)
        createSolicitudGenerada(db)
        createPhTrazable(db)
        createTablePhCalidad(db)
        createCatPhTrazable(db)
        createCatPhCalidad(db)
        createCatConTrazable(db)
        createCatConCalidad(db)
        createConTratamiento(db)
        createColor(db)
        createAforo(db)
        createTipoTratamiento(db)
        createTermometro(db)
        createTermometro2(db)
        createConTrazable(db)
        createConCalidad(db)
        createPhMuestra(db)
        createPhCalidadMuestra(db)
        createGastoMuestra(db)
        createConMuestra(db)
        createTempMuestra(db)
        createTempAmbiente(db)
        createCampoCompuesto(db)
        createObservacionGeneral(db)
        createEvidencia(db)
        createCanceladas(db)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
    fun createCanceladas(db: SQLiteDatabase?){
        val model = "CREATE TABLE " + CANCELADAS + " (" +
                "Id_cancelada INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Folio TEXT NOT NULL," +
                "Id_solicitud INTEGER," +
                "Muestra INTEGER NOT NULL," +
                "Estado INTEGER NOT NULL" +
                ")"
        db?.execSQL(model)
    }
    fun createEvidencia(db: SQLiteDatabase?){
        val model = "CREATE TABLE " + EVIDENCIA + " (" +
                "Id_evidencia INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Folio TEXT NOT NULL," +
                "Codigo TEXT NOT NULL" +
                ")"
        db?.execSQL(model)
    }
    fun createObservacionGeneral(db: SQLiteDatabase?){
        val model = "CREATE TABLE " + OBSGENERAL +" (" +
                "Id_obsGeneral INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Folio TEXT NOT NULL," +
                "Observacion TEXT NOT NULL" +
                ")"
        db?.execSQL(model)
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
                Id_muestreador + " VARCHAR(10)," +
                User + " VARCHAR(100)," +
                UserPass + " VARCHAR(100))"


        db?.execSQL(usuarios_app)
    }
    fun insertCanceladas (can: Canceladas){
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put("Folio", can.Folio)
        cv.put("Id_solicitud", can.Id_solicitud)
        cv.put("Muestra", can.Muestra)
        cv.put("Estado", can.Estado)
        var result = db.insert(CANCELADAS, null,cv)
        if( result == -1.toLong())
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(context, "Observacion guardada", Toast.LENGTH_SHORT).show()
        }
    }

    fun insertObsGeneral(obs: ObservacionGeneral){
        val db = this.writableDatabase
        var cv = ContentValues()
    cv.put("Folio", obs.Folio)
    cv.put("Observacion", obs.Observacion)
    var result = db.insert(OBSGENERAL, null,cv)
    if( result == -1.toLong())
    {
        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
    }
    else
    {
        Toast.makeText(context, "Observacion guardada", Toast.LENGTH_SHORT).show()
    }
}
    fun updateObsGeneral(obsUpdate: ObservacionGeneral){
        try {
            val db = this.writableDatabase
            var cv = ContentValues()
            cv.put("Observacion", obsUpdate.Observacion)
            var result = db.update(OBSGENERAL, null, cv.toString(),
                arrayOf("Folio="+obsUpdate.Folio)
            )

            if( result.equals(-1.toLong()))
            {
                Toast.makeText(context, "Error al actualizar obs", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(context, "Observacion Actualizada", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception){
            Toast.makeText(context,"Error al actualizar obs",Toast.LENGTH_SHORT).show()
        }

    }

    fun updateSolicitudGeneradas(solicitudUpdate: SolicitudGenerada){
        try {
            val db = this.writableDatabase
            var cv = ContentValues()
            cv.put("Estado", solicitudUpdate.Estado)
            var result = db.update(SOLGENERADA, null, cv.toString(),
                arrayOf(("Estado="+solicitudUpdate.Estado))
            )
            if( result.equals(-1.toLong()))
            {
                Toast.makeText(context, "Error al actualizar Solicitud", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(context, "Enviado", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception){
            Toast.makeText(context,"Error al actualizar solicitud generada",Toast.LENGTH_SHORT).show()
        }
    }

    fun insertEvidencia(evidencia: Evidencia){
        val db = this.writableDatabase
        var cv = ContentValues()

    cv.put("Folio",evidencia.FolioEvidencia)
    cv.put("Codigo",evidencia.Codigo)

    var result = db.insert(EVIDENCIA, null,cv)
    if( result == -1.toLong())
    {
        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
    }
    else
    {
        Toast.makeText(context, "Imagen guardada", Toast.LENGTH_SHORT).show()
    }
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
                "Id_direccion INTEGER NOT NULL," +
                "Id_contacto INTEGER NOT NULL," +
                "Observacion VARCHAR(255)," +
                "Servicio VARCHAR(255)," +
                "Descarga VARCHAR(255)," +
                "Clave VARCHAR(255)," +
                "Fecha_muestreo VARCHAR(255)," +
                "Num_tomas  VARCHAR(255)," +
                "Id_muestreador INTEGER," +
                "Estado INTEGER," +
                "Punto TEXT"+
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
        cv.put("Id_direccion", sol.Id_direccion)
        cv.put("Id_contacto", sol.Id_contacto)
        cv.put("Observacion", sol.Observacion)
        cv.put("Servicio", sol.Servicio)
        cv.put("Descarga", sol.Descarga)
        cv.put("Clave", sol.Clave)
        cv.put("Fecha_muestreo", sol.Fecha_muestreo)
        cv.put("Num_tomas", sol.Num_tomas)
        cv.put("Id_muestreador", sol.Id_muestreador)
        cv.put("Estado", sol.Estado)
        cv.put("Punto", sol.Punto)

        var result = db.insert(SOLGENERADA, null,cv)

    }
    // Fin SolicitudGenerada
    //inicio de color, aforo
    fun createTipoTratamiento(db: SQLiteDatabase?){
        val model = "CREATE TABLE " + TIPOTRATAMIENTO + "(" +
                "Id_tipo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Tratamiento VARCHAR(40)" +
                ")"
        db?.execSQL(model)
    }
    fun createAforo(db: SQLiteDatabase?){
        val model = "CREATE TABLE " + AFORO + "(" +
                "Id_aforo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Aforo VARCHAR(40)" +
                ")"
        db?.execSQL(model)
    }
    fun createColor(db: SQLiteDatabase?){
        val model = "CREATE TABLE " + COLOR + "(" +
                "Id_color INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Color VARCHAR(40)" +
                ")"
        db?.execSQL(model)
    }
    fun createConTratamiento(db: SQLiteDatabase?){
        val model = "CREATE TABLE " + CONTRATAMIENTO + "(" +
                "Id_conTratamiento INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ConTratamiento VARCHAR(40)" +
                ")"
        db?.execSQL(model)
    }
    fun insertConTratamiento(con_tratamiento: ConTratamiento){
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put("ConTratamiento", con_tratamiento.ConTratamiento)
        var result = db.insert(CONTRATAMIENTO, null, cv)
        if( result == -1.toLong())
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
    fun insertColor(color: Color){
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put("Color", color.Color)
        var result = db.insert(COLOR, null, cv)
        if( result == -1.toLong())
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
    fun insertAforo(aforo: MetodoAforo){
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put("Aforo", aforo.Aforo)
        var result = db.insert(AFORO, null, cv)
        if( result == -1.toLong())
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
    fun insertTipoTratamiento(tipo: TipoTratamiento){
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put("Tratamiento", tipo.Tratamiento)
        var result = db.insert(TIPOTRATAMIENTO, null, cv)
        if( result == -1.toLong())
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
    //fin de color
    // Inicio Termometro Campo
    fun createTermometro(db: SQLiteDatabase?){
        val model = "CREATE TABLE "+ TERMOMETRO +" (" +
                "Id_termometro INTEGER PRIMARY KEY," +
                "Id_muestreador INTEGER," +
                "Equipo TEXT," +
                "Marca TEXT," +
                "Modelo TEXT," +
                "Serie TEXT" +
                ")"
        db?.execSQL(model)
    }
    fun createTermometro2(db: SQLiteDatabase?){
        val model = "CREATE TABLE "+ TERMOMETRO2 +" (" +
                "Id_termometro INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Id_muestreador INTEGER," +
                "Equipo TEXT, " +
                "Marca TEXT, " +
                "Modelo TEXT," +
                "Serie TEXT " +
                ")"
        db?.execSQL(model)
    }
    fun insertTermometroCampo(term: TermometroCampo) {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put("Id_termometro",term.Id_termometro)
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
    fun insertTermometroCampo2(term2: TermometroCampo2) {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put("Id_muestreador",term2.Id_muestreador)
        cv.put("Equipo", term2.Equipo)
        cv.put("Marca", term2.Marca)
        cv.put("Modelo", term2.Modelo)
        cv.put("Serie", term2.Serie)
        var result = db.insert(TERMOMETRO2, null,cv)
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
                Captura + " VARCHAR(255)," +
                Id_equipo + " VARCHAR(255)," +
                Id_equipo2 + " VARCHAR(255)," +
                Temperatura_a + " VARCHAR(255)," +
                Temperatura_b + " VARCHAR(255)," +
                Latitud + " VARCHAR(255)," +
                Longitud + " VARCHAR(255)," +
                Pendiente + " VARCHAR(255)," +
                Criterio + " VARCHAR(255)," +
                Supervisor + " VARCHAR(255))"
        db?.execSQL(campo_general)
    }

    fun insertGeneral(generales: Generales) {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(Id_solicitud,generales.Id_solicitud)
        cv.put(Captura, generales.Captura)
        cv.put(Id_equipo, generales.Id_equipo)
        cv.put(Id_equipo2, generales.Id_equipo2)
        cv.put(Temperatura_a, generales.Temperatura_a)
        cv.put(Temperatura_b, generales.Temperatura_b)
        cv.put(Latitud, generales.Latitud)
        cv.put(Longitud, generales.Longitud)
        cv.put(Pendiente, generales.Pendiente)
        cv.put(Criterio, generales.Criterio)
        cv.put(Supervisor, generales.Supervisor)
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
                "Id_phCalidad INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Id_solicitud INTEGER," +
                "Ph_calidad INTEGER," +
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
        cv.put("Id_solicitud",phCalidad.Id_solicitud)
        cv.put("Ph_calidad",phCalidad.Ph_calidad)
        cv.put("Lectura1", phCalidad.Lectura1)
        cv.put("Lectura2", phCalidad.Lectura2)
        cv.put("Lectura3", phCalidad.Lectura3)
        cv.put("Estado", phCalidad.Estado)
        cv.put("Promedio", phCalidad.Promedio)

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
                "Num_toma INTEGER," +
                "Materia VARCHAR(100)," +
                "Olor VARCHAR(100)," +
                "Color VARCHAR(100)," +
                "Ph1 FLOAT," +
                "Ph2 FLOAT," +
                "Ph3 FLOAT," +
                "Promedio FLOAT," +
                "Fecha VARCHAR(100)," +
                "Hora VARCHAR(100)" +
                ")"
        db?.execSQL(ph)
    }
    fun insertPhMuestra(phMuestra: PhMuestra) {
        val db = this.writableDatabase
        var cv = ContentValues()
        //put datos
        cv.put("Id_solicitud", phMuestra.Id_solicitud)
        cv.put("Num_toma", phMuestra.Num_muestra)
        cv.put("Materia", phMuestra.Materia)
        cv.put("Olor", phMuestra.Olor)
        cv.put("Color", phMuestra.Color)
        cv.put("Ph1", phMuestra.Ph1)
        cv.put("Ph2", phMuestra.Ph2)
        cv.put("Ph3", phMuestra.Ph3)
        cv.put("Promedio", phMuestra.Promedio)
        cv.put("Fecha", phMuestra.Fecha)
        cv.put("Hora", phMuestra.Hora)

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
    fun createPhCalidadMuestra(db: SQLiteDatabase?){
        val phCalidadMuestra = "CREATE TABLE " + PHCALIDADMUESTRA + " (" +
                "Id_phCalidad INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Id_solicitud INTEGER," +
                "Num_toma INTEGER," +
                "Lectura1C FLOAT," +
                "Lectura2C FLOAT," +
                "Lectura3C FLOAT," +
                "Estado TEXT," +
                "PromedioC FLOAT" +
                ")"
        db?.execSQL(phCalidadMuestra)
    }
    fun insertPhCalidadMuestra(phCalidadMuestra: PhCalidadMuestra){
        val db =  this.writableDatabase
        var cv = ContentValues()
        cv.put("Id_solicitud", phCalidadMuestra.Id_solicitud)
        cv.put("Num_toma", phCalidadMuestra.Num_toma)
        cv.put("Lectura1C", phCalidadMuestra.Lectura1C)
        cv.put("Lectura2C", phCalidadMuestra.Lectura2C)
        cv.put("Lectura3C", phCalidadMuestra.Lectura3C)
        cv.put("Estado", phCalidadMuestra.Estado)
        cv.put("PromedioC", phCalidadMuestra.PromedioC)
        var result = db.insert(PHCALIDADMUESTRA, null,cv)
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
                "Num_toma INTEGER," +
                "Conductividad1 FLOAT," +
                "Conductividad2 FLOAT," +
                "Conductividad3 FLOAT," +
                "Promedio FLOAT" +
                ")"
        db?.execSQL(conduc)
    }
    fun insertConMuestra(conMuestra: ConMuestra) {
        val db = this.writableDatabase
        var cv = ContentValues()
        //put datos
        cv.put("Id_solicitud", conMuestra.Id_solicitud)
        cv.put("Num_toma", conMuestra.Num_muestra)
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
                "Num_toma INTEGER," +
                "Temp1 FLOAT," +
                "Temp2 FLOAT," +
                "Temp3 FLOAT," +
                "Promedio FLOAT" +
                ")"
        db?.execSQL(ph)
    }
    fun createTempAmbiente(db: SQLiteDatabase?){
        val ambiente = "CREATE TABLE " + TEMPAMBIENTE + " (" +
                "Id_temperaturaA INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Id_solicitud INTEGER," +
                "Num_toma INTEGER," +
                "TempA1 FLOAT," +
                "TempA2 FLOAT," +
                "TempA3 FLOAT," +
                "PromedioA FLOAT" +
                ")"
        db?.execSQL(ambiente)
    }
    fun insertTempMuestra(tempMuestra: TempMuestra) {
        val db = this.writableDatabase
        var cv = ContentValues()
        //put datos
        cv.put("Id_solicitud", tempMuestra.Id_solicitud)
        cv.put("Num_toma", tempMuestra.Num_muestra)
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
    fun insertTempAmbiente(tempAmbiente: TempAmbiente) {
        val db = this.writableDatabase
        var cv = ContentValues()
        //put datos
        cv.put("Id_solicitud", tempAmbiente.Id_solicitud)
        cv.put("Num_toma", tempAmbiente.Num_muestra)
        cv.put("TempA1", tempAmbiente.TempA1)
        cv.put("TempA2", tempAmbiente.TempA2)
        cv.put("TempA3", tempAmbiente.TempA3)
        cv.put("PromedioA", tempAmbiente.PromedioA)

        var result = db.insert(TEMPAMBIENTE, null,cv)
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
                "Num_toma INTEGER," +
                "Gasto1 FLOAT," +
                "Gasto2 FLOAT," +
                "Gasto3 FLOAT," +
                "Promedio FLOAT" +
                ")"
        db?.execSQL(ph)
    }
    fun insertGastoMuestra(gastoMuestra: GastoMuestra) {
        val db = this.writableDatabase
        var cv = ContentValues()
        //put datos
        cv.put("Id_solicitud", gastoMuestra.Id_solicitud)
        cv.put("Num_toma", gastoMuestra.Num_muestra)
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
    fun createCampoCompuesto(db: SQLiteDatabase?){
        val ph = "CREATE TABLE " + CAMPOCOMPUESTO + " (" +
                "Id_campo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Id_solicitud INTEGER," +
                "Metodo_aforo TEXT," +
                "Con_tratamiento TEXT," +
                "Tipo_tratamiento TEXT," +
                "Proc_muestreo TEXT," +
                "Observaciones TEXT," +
                "Obser_solicitud TEXT," +
                "Ph_muestraComp TEXT," +
                "Temp_muestraComp TEXT," +
                "Volumen_calculado TEXT," +
                "Cloruros TEXT" +
                ")"
        db?.execSQL(ph)
    }
    fun insertCampoCompuesto(campoMuestra: CampoCompuesto) {
        val db = this.writableDatabase
        var cv = ContentValues()
        //put datos
        cv.put("Id_solicitud", campoMuestra.Id_solicitud)
        cv.put("Metodo_aforo", campoMuestra.Aforo)
        cv.put("Con_tratamiento", campoMuestra.ConTratamiento)
        cv.put("Tipo_tratamiento", campoMuestra.TipoTratamiento)
        cv.put("Proc_muestreo", campoMuestra.ProcMuestreo)
        cv.put("Observaciones", campoMuestra.Observaciones)
        cv.put("Obser_solicitud", campoMuestra.ObserSolicitud)
        cv.put("Ph_muestraComp", campoMuestra.PhMuestraCom)
        cv.put("Temp_muestraComp", campoMuestra.Temp_muestraComp)
        cv.put("Volumen_calculado", campoMuestra.VolCalculado)
        cv.put("Cloruros", campoMuestra.Cloruros)


        var result = db.insert(CAMPOCOMPUESTO, null,cv)
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
