package com.acama.muestreoapp

import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.get
import com.acama.muestreoapp.agua.MuestraSimple
import com.acama.muestreoapp.api.VolleySingleton
import com.acama.muestreoapp.databinding.ActivityListaAguaBinding
import com.acama.muestreoapp.models.*
import com.acama.muestreoapp.preference.UserApplication
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class ListaAguaActivity : AppCompatActivity() {
    private lateinit var bin: ActivityListaAguaBinding
    private lateinit var listaArr: MutableList<String>
    private lateinit var con: DataBaseHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityListaAguaBinding.inflate(layoutInflater)
        setContentView(bin.root)

        con = DataBaseHandler(this)
        llenarLista()
        //Mostrar datos en la lista
        bin.imgSincronizar.setOnClickListener {
            //val del = "DELETE FROM solicitud_generadas where Id_solicitudGen = 1"
            //con.deleteData(con.writableDatabase, del)
            //animationSycn(bin.lteCarga,com.acama.muestreoapp.R.raw.cargaar)
            sycnDatos()
        }

    }

    fun animationSycn(imageView: LottieAnimationView, animation: Int) {
        imageView.setAnimation(animation)
        imageView.playAnimation()
    }

    fun sycnDatos() {
        CoroutineScope(Dispatchers.IO).launch {

            var listSol: MutableList<String> = ArrayList()
            var solGenModel: MutableList<String> = ArrayList()

            val db: SQLiteDatabase = con.readableDatabase
            val query = "SELECT * FROM solicitud_generadas WHERE Estado != 1"
            val result = db.rawQuery(query, null)
            Log.d("result", result.toString())

            var cont: Int = 0
            if (result.moveToFirst()) {
                do {

                    var json = "{" +
                            " \"Id_solicitudGen\" : \"" + result.getInt(0) + "\"" +
                            ",\"Folio_servicio\" : \"" + result.getString(1) + "\"" +
                            ",\"Id_solicitud\" : \"" + result.getInt(2) + "\"" +
                            ",\"Id_intermediario\" : \"" + result.getInt(3) + "\"" +
                            ",\"Nombres\" : \"" + result.getString(4) + "\"" +
                            ",\"Id_cliente\" : \"" + result.getInt(5) + "\"" +
                            ",\"Empresa\" : \"" + result.getString(6) + "\"" +
                            ",\"Direccion\" : \"" + result.getString(7) + "\"" +
                            ",\"Contacto\" : \"" + result.getString(8) + "\"" +
                            ",\"Servicio\" : \"" + result.getString(9) + "\"" +
                            ",\"Descarga\" : \"" + result.getString(10) + "\"" +
                            ",\"Clave\" : \"" + result.getString(11) + "\"" +
                            ",\"Fecha_muestreo\" : \"" + result.getString(12) + "\"" +
                            ",\"Num_tomas\" : \"" + result.getInt(13) + "\"" +
                            ",\"Id_muestreador\" : \"" + result.getInt(14) + "\"" +
                            ",\"Estado\" : \"" + result.getInt(15) + "\"" +
                            "}"
                    listSol.add(cont, json)
                    cont++
                } while (result.moveToNext())
            }
            solGenModel.addAll(listSol)

            Log.d("json", solGenModel.toString())

            val stringRequest = object : StringRequest(
                Request.Method.POST, UserApplication.prefs.BASE_URL + "sycnDatos",
                Response.Listener<String> { response ->
                    try {
                        val obj = JSONObject(response)
                        Log.d("Response", response)
                        if (obj.getBoolean("response") == true) {
                            Log.d("datos", obj.getString("datos"))
                            //Log.d("ds",obj.getString("ds"))
                            guardarDatos(obj)
                            llenarLista()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Error en obtener los datos",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(
                            applicationContext,
                            "Error en la solicitud",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                },
                Response.ErrorListener { volleyError ->
                    Toast.makeText(
                        applicationContext,
                        volleyError.message,
                        Toast.LENGTH_LONG
                    ).show()
                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()
                    params.put("solicitudesModel", solGenModel.toString())
                    params.put("idMuestreador", UserApplication.prefs.getMuestreadorId())
                    return params
                }
            }
            // Agregamos el request para que nos permita retornar y/o visualizar la respuesta
            VolleySingleton.getInstance(this@ListaAguaActivity).addToRequestQueue(stringRequest)
        }
    }

    fun llenarLista() {

        val listaMuestreo: MutableList<String> = mutableListOf()
        val listaIdMuestreo: MutableList<String> = mutableListOf()


        val db: SQLiteDatabase = con.readableDatabase
        val idMuestreador = UserApplication.prefs.getMuestreadorId()
        val query =
            "SELECT * FROM solicitud_generadas WHERE Id_muestreador = '$idMuestreador'"
        val muestreoModel = db.rawQuery(query, null)
        if (muestreoModel.moveToFirst()) {
            do {
                listaMuestreo.add("" + muestreoModel.getString(1) + "\n" + muestreoModel.getString(6))
                listaIdMuestreo.add(muestreoModel.getString(1))
            } while (muestreoModel.moveToNext())
            // Toast.makeText(this,"Sesión satisfactoria",Toast.LENGTH_SHORT).show()
        } else {
            // Toast.makeText(this,"Usuario y/o contraseña incorrecto",Toast.LENGTH_SHORT).show()
        }

        val adaptador1 = ArrayAdapter<String>(this, R.layout.simple_list_item_1, listaMuestreo)
        bin.lstMuestreos.adapter = adaptador1

        bin.lstMuestreos.setOnItemClickListener { adapterView, view, i, l ->
            //acción que cada elemen to debe llevar
            val intent = Intent(this, AguaCapturaActivity::class.java)
            intent.putExtra("folio", listaIdMuestreo[i])
            startActivity(intent)
            //Toast.makeText(this,"Folio: " + listaIdMuestreo[i] ,Toast.LENGTH_SHORT).show()
            /*
            Toast.makeText(this,"Folio: " + ,Toast.LENGTH_SHORT).show()
            Log.d("Folio",bin.lstMuestreos.getItemAtPosition(i).toString())
            Log.d("Folio2",bin.lstMuestreos.getItemIdAtPosition(i).toString())
            */
        }
        listaArr = listaIdMuestreo
        registerForContextMenu(bin.lstMuestreos)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?,
    ) {
        menuInflater.inflate(com.acama.muestreoapp.R.menu.menu_lista, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        val db: SQLiteDatabase = con.readableDatabase
        //val idMuestreador = UserApplication.prefs.getMuestreadorId()
        var sw: Boolean = false
        var folio = listaArr[posicion]
        var idSol: Int = 0
        when (item.itemId) {
            com.acama.muestreoapp.R.id.enviar -> {
                //Log.d("item",)
                val query = "SELECT * FROM solicitud_generadas WHERE Folio_servicio = '$folio'"
                val muestreoModel = db.rawQuery(query, null)

                if (muestreoModel.moveToFirst()) {
                    do {
                        if (muestreoModel.getInt(16) == 1) {
                            sw = true
                            idSol = muestreoModel.getInt(0)
                        } else {
                            sw = false
                        }
                    } while (muestreoModel.moveToNext())
                }

                if (sw == true) {
                    sendDatosMuestra(idSol, folio)
                    Toast.makeText(this, "Datos guardados correctamete", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        this,
                        "No puedes enviar aun esta orden de muestra",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                //Toast.makeText(applicationContext, "Enviar", Toast.LENGTH_SHORT).show()
                return true
            }
            com.acama.muestreoapp.R.id.info -> {
                Toast.makeText(applicationContext, "info", Toast.LENGTH_SHORT).show()
                return true
            }
            com.acama.muestreoapp.R.id.firma -> {
                Toast.makeText(applicationContext, "Firma", Toast.LENGTH_SHORT).show()
                return true
            }
        }

        return super.onContextItemSelected(item)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("optionSelected", item.toString())
        return super.onOptionsItemSelected(item)
    }

    fun sendDatosMuestra(idSol: Int, folio: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val db: SQLiteDatabase = con.readableDatabase
            // val query = "SELECT * FROM solicitud_generadas WHERE Folio_servicio = '$folio'"
            val queryGeneral = "SELECT * FROM campo_generales WHERE Id_solicitud = '$idSol'"
            val queryPhTra = "SELECT * FROM ph_trazable WHERE Id_solicitud = '$idSol'"
            val queryPhCal = "SELECT * FROM ph_calidad WHERE Id_solicitud = '$idSol'"
            val queryConTra = "SELECT * FROM con_trazable WHERE Id_solicitud = '$idSol'"
            val queryConCal = "SELECT * FROM con_trazable WHERE Id_solicitud = '$idSol'"

            var listTemp: MutableList<String> = ArrayList()

            var solGenModel: MutableList<String> = ArrayList()
            var campoGenModel: MutableList<String> = ArrayList()
            var phTrazable: MutableList<String> = ArrayList()
            var phCalidad: MutableList<String> = ArrayList()
            var conTrazable: MutableList<String> = ArrayList()
            var conCalidad: MutableList<String> = ArrayList()

            //Llenar datos generales
            val generalModel = db.rawQuery(queryGeneral, null)
            var cont: Int = 0
            if (generalModel.moveToFirst()) {
                do {
                    Log.d("Captura",generalModel.getString(2))
                    var jsonGeneral = "{" +
                            " \"Id_solicitud\" : \"" + generalModel.getInt(1) + "\"" +
                            ", \"Captura\" : \"" + generalModel.getString(2) + "\"" +
                            ", \"Id_equipo\" : \"" + generalModel.getInt(3) + "\"" +
                            ", \"Temperatura_a\" : \"" + generalModel.getString(4) + "\"" +
                            ", \"Temperatura_b\" : \"" + generalModel.getString(5) + "\"" +
                            ", \"Latitud\" : \"" + generalModel.getString(6) + "\"" +
                            ", \"Longitud\" : \"" + generalModel.getString(7) + "\"" +
                            ", \"Pendiente\" : \"" + generalModel.getString(8) + "\"" +
                            ", \"Criterio\" : \"" + generalModel.getString(9) + "\"" +
                            "}"

                    listTemp.add(cont, jsonGeneral)
                    cont++
                    Log.d("Lista Temp",listTemp.toString())
                } while (generalModel.moveToNext())
            }
            campoGenModel.addAll(listTemp)
            //Llenar trazable/calidad generales
            var listTempPhT: MutableList<String> = ArrayList()
            val phTraModel = db.rawQuery(queryPhTra, null)
            cont = 0
            if (phTraModel.moveToFirst()) {
                do {
                    //Log.d("Captura",generalModel.getString(2))
                    var jsonPhTra = "{" +
                            " \"Id_solicitud\" : \"" + phTraModel.getInt(1) + "\"" +
                            ", \"Id_phTrazable\" : \"" + phTraModel.getString(2) + "\"" +
                            ", \"Lectura1\" : \"" + phTraModel.getInt(3) + "\"" +
                            ", \"Lectura2\" : \"" + phTraModel.getString(4) + "\"" +
                            ", \"Lectura3\" : \"" + phTraModel.getString(5) + "\"" +
                            ", \"Estado\" : \"" + phTraModel.getString(6) + "\"" +
                            "}"

                    listTempPhT.add(cont, jsonPhTra)
                    cont++
                    //Log.d("Lista Temp",listTemp.toString())
                } while (phTraModel.moveToNext())
            }
            phTrazable.addAll(listTempPhT)

            var listTempPhC: MutableList<String> = ArrayList()
            val phCalModel = db.rawQuery(queryPhCal, null)
            cont = 0
            if (phCalModel.moveToFirst()) {
                do {
                    //Log.d("Captura",generalModel.getString(2))
                    var jsonPhCal = "{" +
                            " \"Id_solicitud\" : \"" + phCalModel.getInt(1) + "\"" +
                            ", \"Id_phCalidad\" : \"" + phCalModel.getString(2) + "\"" +
                            ", \"Lectura1\" : \"" + phCalModel.getInt(3) + "\"" +
                            ", \"Lectura2\" : \"" + phCalModel.getString(4) + "\"" +
                            ", \"Lectura3\" : \"" + phCalModel.getString(5) + "\"" +
                            ", \"Estado\" : \"" + phCalModel.getString(6) + "\"" +
                            ", \"Promedio\" : \"" + phCalModel.getString(7) + "\"" +
                            "}"

                    listTempPhC.add(cont, jsonPhCal)
                    cont++
                    //Log.d("Lista Temp",listTemp.toString())
                } while (phCalModel.moveToNext())
            }
            phCalidad.addAll(listTempPhC)

            var listTempConT: MutableList<String> = ArrayList()
            val conTraModel = db.rawQuery(queryConTra, null)
            cont = 0
            if (conTraModel.moveToFirst()) {
                do {
                    //Log.d("Captura",generalModel.getString(2))
                    var jsonConTra = "{" +
                            " \"Id_solicitud\" : \"" + conTraModel.getInt(1) + "\"" +
                            ", \"Id_conTrazable\" : \"" + conTraModel.getString(2) + "\"" +
                            ", \"Lectura1\" : \"" + conTraModel.getInt(3) + "\"" +
                            ", \"Lectura2\" : \"" + conTraModel.getString(4) + "\"" +
                            ", \"Lectura3\" : \"" + conTraModel.getString(5) + "\"" +
                            ", \"Estado\" : \"" + conTraModel.getString(6) + "\"" +
                            "}"

                    listTempConT.add(cont, jsonConTra)
                    cont++
                    //Log.d("Lista Temp",listTemp.toString())
                } while (conTraModel.moveToNext())
            }
            conTrazable.addAll(listTempConT)

            var listTempConC: MutableList<String> = ArrayList()
            val conCalModel = db.rawQuery(queryConTra, null)
            cont = 0
            if (conCalModel.moveToFirst()) {
                do {
                    //Log.d("Captura",generalModel.getString(2))
                    var jsonConCal = "{" +
                            " \"Id_solicitud\" : \"" + conCalModel.getInt(1) + "\"" +
                            ", \"Id_conCalidad\" : \"" + conCalModel.getString(2) + "\"" +
                            ", \"Lectura1\" : \"" + conCalModel.getInt(3) + "\"" +
                            ", \"Lectura2\" : \"" + conCalModel.getString(4) + "\"" +
                            ", \"Lectura3\" : \"" + conCalModel.getString(5) + "\"" +
                            ", \"Estado\" : \"" + conCalModel.getString(6) + "\"" +
                            ", \"Promedio\" : \"" + conCalModel.getString(6) + "\"" +
                            "}"

                    listTempConC.add(cont, jsonConCal)
                    cont++
                    //Log.d("Lista Temp",listTemp.toString())
                } while (conCalModel.moveToNext())
            }
            conCalidad.addAll(listTempConC)

            val stringRequest = object : StringRequest(
                Request.Method.POST, UserApplication.prefs.BASE_URL + "enviarDatos",
                Response.Listener<String> { response ->
                    try {
                        val obj = JSONObject(response)
                        Log.d("Response", response)
                        if (obj.getBoolean("response") == true) {
                            Log.d("datos", obj.getString("dato"))

                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Error en la solicitud",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(
                            applicationContext,
                            "Error en la solicitud",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                },
                Response.ErrorListener { volleyError ->
                    Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG)
                        .show()
                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()
                    params.put("campoGenerales", campoGenModel.toString())
                    params.put("phTrazable", phTrazable.toString())
                    params.put("phCalidad", phCalidad.toString())
                    params.put("conTrazable", conTrazable.toString())
                    params.put("conCalidad", conCalidad.toString())
                    params.put("idMuestreador", UserApplication.prefs.getMuestreadorId())
                    params.put("folio", folio)
                    return params
                }
            }
            // Agregamos el request para que nos permita retornar y/o visualizar la respuesta
            VolleySingleton.getInstance(this@ListaAguaActivity).addToRequestQueue(stringRequest)

        }

    }

    fun guardarDatos(data: JSONObject) {
        //Log.d("syncFirstData",data.getString("usuarios"))
        //Log.d("jsonArray",json_array[0].toString())

        var listaMuestreo = JSONArray(data.getString("modelSolGen"))
        var listaPhTrazable = JSONArray(data.getString("phTrazable"))
        var listaPhCalidad = JSONArray(data.getString("phCalidad"))
        var listaTermometro = JSONArray(data.getString("termometro"))
        var listaConTrazable = JSONArray(data.getString("conTrazable"))
        var listaConCalidad = JSONArray(data.getString("conCalidad"))

        val db: SQLiteDatabase = con.readableDatabase


        //Sincronizar datos muestreo
        for (i in 0 until listaMuestreo.length()) {
            val muestreo = listaMuestreo.getJSONObject(i)
            val folio = muestreo.getString("Folio_servicio").toString()
            val query = "SELECT * FROM solicitud_generadas WHERE Folio_servicio = '$folio'"
            val solGenModel = db.rawQuery(query, null)
            //Log.d("Solicitud", muestreo.getInt("Id_solicitud").toString())
            var cont: Int = 0
            var idSolGen: Int = 0
            if (solGenModel.moveToFirst()) {
                do {

                } while (solGenModel.moveToNext())
            } else {
                var muestreoModel = SolicitudGenerada(
                    muestreo.getString("Folio_servicio"),
                    muestreo.getInt("Id_solicitud"),
                    muestreo.getInt("Id_intermediario"),
                    muestreo.getString("Nombres"),
                    muestreo.getInt("Id_cliente"),
                    muestreo.getString("Empresa"),
                    muestreo.getString("Direccion"),
                    muestreo.getString("Nom_con"),
                    muestreo.getString("Observacion"),
                    muestreo.getString("Servicio"),
                    muestreo.getString("Descarga"),
                    muestreo.getString("Clave"),
                    muestreo.getString("Fecha_muestreo"),
                    muestreo.getInt("Num_tomas"),
                    muestreo.getInt("Id_muestreador"),
                    1
                )
                var db = DataBaseHandler(this)
                db.insertSolicitudGenerada(muestreoModel)
            }

            val query2 = "SELECT * FROM solicitud_generadas WHERE Folio_servicio = '$folio'"
            val solGenModel2 = db.rawQuery(query2, null)
            if (solGenModel2.moveToFirst()) {
                do {
                    idSolGen = solGenModel2.getInt(0)
                    var generalModel = Generales(
                        idSolGen,
                        "",
                        0,
                        "",
                        "",
                        "",
                        "",
                        "",
                        ""
                    )
                    var db = DataBaseHandler(this)
                    db.insertGeneral(generalModel)
                    // Creat PhCalidad Muestra
                    var phCalModel = PhCalidad(
                        idSolGen,
                        0,
                        "",
                        "",
                        "",
                        "",
                        ""
                    )
                    db.insertPhCalidad(phCalModel)
                    db.insertPhCalidad(phCalModel)
                    // Crear Ph Trazable muestra
                    var phTraModel = PhTrazable(
                        idSolGen,
                        0,
                        "",
                        "",
                        "",
                        ""
                    )
                    db.insertPhTrazable(phTraModel)
                    db.insertPhTrazable(phTraModel)

                    var conTraModel = ConTrazable(
                        idSolGen,
                        0,
                        "",
                        "",
                        "",
                        ""
                    )
                    db.insertConTrazable(conTraModel)
                    var conCalModel = ConCalidad(
                        idSolGen,
                        0,
                        "",
                        "",
                        "",
                        "",
                        ""
                    )
                    db.insertConCalidad(conCalModel)


                } while (solGenModel2.moveToNext())
            }

        }
        //Sincronizar datos termometro
        for (i in 0 until listaTermometro.length()) {
            val termometro = listaTermometro.getJSONObject(i)
            val equipo = termometro.getString("Equipo").toString()
            val queryTermo = "SELECT * FROM TermometroCampo WHERE Equipo = '$equipo'"
            val termometroCampo = db.rawQuery(queryTermo, null)
            //Log.d("Solicitud", muestreo.getInt("Id_solicitud").toString())
            var cont: Int = 0
            if (termometroCampo.moveToFirst()) {
                do {

                } while (termometroCampo.moveToNext())
            } else {
                var termometroModel = TermometroCampo(
                    termometro.getString("Id_muestreador").toInt(),
                    termometro.getString("Equipo"),
                    termometro.getString("Marca"),
                    termometro.getString("Modelo"),
                    termometro.getString("Serie")
                )
                var db = DataBaseHandler(this)
                db.insertTermometroCampo(termometroModel)
            }
        }

        //Sincronizar ph Trazable
        for (i in 0 until listaPhTrazable.length()) {
            val phTrazable = listaPhTrazable.getJSONObject(i)
            val ph = phTrazable.getString("Ph").toString()
            val lote = phTrazable.getString("Lote").toString()
            val queryPhTrazable = "SELECT * FROM cat_phTrazable WHERE Ph = '$ph' AND Lote = '$lote'"
            val phTrazableCampo = db.rawQuery(queryPhTrazable, null)
            //Log.d("Solicitud", muestreo.getInt("Id_solicitud").toString())
            var cont: Int = 0
            if (phTrazableCampo.moveToFirst()) {
                do {

                } while (phTrazableCampo.moveToNext())
            } else {
                var phTModel = CatPhTrazable(
                    phTrazable.getString("Ph"),
                    phTrazable.getString("Marca"),
                    phTrazable.getString("Lote"),
                    phTrazable.getString("Inicio_caducidad"),
                    phTrazable.getString("Fin_caducidad")
                )
                var db = DataBaseHandler(this)
                db.insertCatPhTrazable(phTModel)
            }
        }
        //Sincronizar ph Calidad
        for (i in 0 until listaPhCalidad.length()) {
            val phCalidad = listaPhCalidad.getJSONObject(i)
            val ph = phCalidad.getString("Ph_calidad").toString()
            val lote = phCalidad.getString("Lote").toString()
            val queryPhCal =
                "SELECT * FROM cat_phCalidad WHERE Ph_calidad = '$ph' AND Lote = '$lote'"
            val phCalCampo = db.rawQuery(queryPhCal, null)
            //Log.d("Solicitud", muestreo.getInt("Id_solicitud").toString())
            var cont: Int = 0
            if (phCalCampo.moveToFirst()) {
                do {

                } while (phCalCampo.moveToNext())
            } else {
                var phModelCal = CatPhCalidad(
                    phCalidad.getString("Ph_calidad"),
                    phCalidad.getString("Marca"),
                    phCalidad.getString("Lote"),
                    phCalidad.getString("Inicio_caducidad"),
                    phCalidad.getString("Fin_caducidad")
                )
                var db = DataBaseHandler(this)
                db.insertCatPhCalidad(phModelCal)
            }
        }

        //Sincronizar con Trazable
        for (i in 0 until listaConTrazable.length()) {
            val conTrazable = listaConTrazable.getJSONObject(i)
            val cond = conTrazable.getString("Conductividad").toString()
            val queryConTra =
                "SELECT * FROM cat_conTrazable WHERE Conductividad = '$cond'"
            val conTraCamp = db.rawQuery(queryConTra, null)
            //Log.d("Solicitud", muestreo.getInt("Id_solicitud").toString())
            var cont: Int = 0
            if (conTraCamp.moveToFirst()) {
                do {

                } while (conTraCamp.moveToNext())
            } else {
                var conTraModel = CatConTrazable(
                    conTrazable.getString("Conductividad"),
                    conTrazable.getString("Marca"),
                    conTrazable.getString("Lote"),
                    conTrazable.getString("Inicio_caducidad"),
                    conTrazable.getString("Fin_caducidad")
                )
                var db = DataBaseHandler(this)
                db.insertCaTConTrazable(conTraModel)
            }
        }
        //Sincronizar con Calidad
        for (i in 0 until listaConCalidad.length()) {
            val conCalidad = listaConCalidad.getJSONObject(i)
            val condC = conCalidad.getString("Conductividad").toString()
            val queryConTra =
                "SELECT * FROM cat_conTrazable WHERE Conductividad = '$condC'"
            val conTraCamp = db.rawQuery(queryConTra, null)
            //Log.d("Solicitud", muestreo.getInt("Id_solicitud").toString())
            var cont: Int = 0
            if (conTraCamp.moveToFirst()) {
                do {

                } while (conTraCamp.moveToNext())
            } else {
                var conCalModel = CatConCalidad(
                    conCalidad.getString("Conductividad"),
                    conCalidad.getString("Marca"),
                    conCalidad.getString("Lote"),
                    conCalidad.getString("Inicio_caducidad"),
                    conCalidad.getString("Fin_caducidad")
                )
                var db = DataBaseHandler(this)
                db.insertCaTConCalidad(conCalModel)
            }
        }

    }

    @SuppressLint("ServiceCast")
    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(bin.edtBuscar.windowToken, 0)
    }

    private fun searchFolio() {

    }

}