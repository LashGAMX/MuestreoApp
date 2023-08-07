package com.acama.muestreoapp

import android.R
import android.annotation.SuppressLint
import android.content.ContentValues
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
import com.acama.muestreoapp.models.PhCalidad
import com.acama.muestreoapp.preference.Prefs
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
import kotlin.math.log


class ListaAguaActivity : AppCompatActivity() {
    private lateinit var bin: ActivityListaAguaBinding
    private lateinit var listaArr: MutableList<String>
    private lateinit var con: DataBaseHandler
    private var pruebaCod : String = ""
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
                            ",\"Id_direccion\" : \"" + result.getInt(7) + "\"" +
                            ",\"Id_contacto\" : \"" + result.getInt(8) + "\"" +
                            ",\"Punto\" : \"" + result.getInt(9) + "\"" +
                            ",\"Servicio\" : \"" + result.getString(10) + "\"" +
                            ",\"Descarga\" : \"" + result.getString(11) + "\"" +
                            ",\"Clave\" : \"" + result.getString(12) + "\"" +
                            ",\"Fecha_muestreo\" : \"" + result.getString(13) + "\"" +
                            ",\"Num_tomas\" : \"" + result.getString(14) + "\"" +
                            ",\"Id_muestreador\" : \"" + result.getInt(15) + "\"" +
                            ",\"Estado\" : \"" + result.getInt(16) + "\"" +
                            "}"
                    listSol.add(cont, json)
                    cont++
                } while (result.moveToNext())
            }
            //solGenModel.addAll(listSol)

            //Log.d("json", solGenModel.toString())
            Log.d("idMuestreador",UserApplication.prefs.getMuestreadorId())

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
                    //params.put("solicitudesModel", solGenModel.toString())
                    params.put("idMuestreador", UserApplication.prefs.getMuestreadorId())
                    return params
                }
            }
            // Agregamos el request para que nos permita retornar y/o visualizar la respuesta
            VolleySingleton.getInstance(this@ListaAguaActivity).addToRequestQueue(stringRequest)
        }
    }

    fun llenarLista() {
        Log.d("Function:","Entro a funcion de llanar lista")
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
        var dbw: SQLiteDatabase = con.writableDatabase
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
                    //actualizar la solicidud al estado 3


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

            val querySolGenerada = "SELECT * FROM solicitud_generadas WHERE Id_solicitudGen = '$idSol'"
            val queryGeneral = "SELECT * FROM campo_generales WHERE Id_solicitud = '$idSol'"
            val queryPhTra = "SELECT * FROM ph_trazable WHERE Id_solicitud = '$idSol'"
            val queryPhCal = "SELECT * FROM ph_calidad WHERE Id_solicitud = '$idSol'"
            val queryConTra = "SELECT * FROM con_trazable WHERE Id_solicitud = '$idSol'"
            val queryConCal = "SELECT * FROM con_calidad WHERE Id_solicitud = '$idSol'"
            val queryPhMuestra = "SELECT * FROM ph_muestra WHERE Id_solicitud = '$idSol'"
            val queryTempMuestra = "SELECT * FROM temperatura_muestra WHERE Id_solicitud = '$idSol'"
            val queryTempAmbiente = "SELECT * FROM temperatura_ambiente WHERE Id_solicitud = '$idSol'"
            val queryConMuestra = "SELECT * FROM conductividad_muestra WHERE Id_solicitud = '$idSol'"
            val queryGastoMuestra = "SELECT * FROM gasto_muestra WHERE Id_solicitud = '$idSol'"
            val queryCampoCompuesto = "SELECT * FROM campo_compuesto WHERE Id_solicitud = '$idSol'"
            val queryEvidencia = "SELECT * FROM evidencia WHERE Folio = '$folio'"
            val queryPhCalidadMuestra = "SELECT * FROM ph_calidadMuestra WHERE Id_solicitud = '$idSol' ORDER BY Num_toma DESC"
            val queryCanceladas = "SELECT * FROM canceladas WHERE Id_solicitud = '$idSol'"
            var listTemp: MutableList<String> = ArrayList()

            var solGenModel: MutableList<String> = ArrayList()
            var solPuntoModel: MutableList<String> = ArrayList()
            var campoGenModel: MutableList<String> = ArrayList()
            var phTrazable: MutableList<String> = ArrayList()
            var phCalidad: MutableList<String> = ArrayList()
            var conTrazable: MutableList<String> = ArrayList()
            var conCalidad: MutableList<String> = ArrayList()
            var phMuestra : MutableList<String> = ArrayList()
            var tempMuestra : MutableList<String> = ArrayList()
            var tempAmbiente : MutableList<String> = ArrayList()
            var conMuestra : MutableList<String> = ArrayList()
            var gastoMuestra : MutableList<String> = ArrayList()
            var campoCompuesto : MutableList<String> = ArrayList()
            var evidencia : MutableList<String> = ArrayList()
            var phCalidadMuestra : MutableList<String> = ArrayList()
            var canceladas: MutableList<String> = ArrayList()



            //Llenar datos generales
            val generalModel = db.rawQuery(queryGeneral, null)
            var cont: Int = 0
            if (generalModel.moveToFirst()) {
                do {
                    //Log.d("Captura",generalModel.getString(2))
                    var jsonGeneral = "{" +
                            " \"Id_solicitud\" : \"" + generalModel.getInt(1) + "\"" +
                            ", \"Captura\" : \"" + generalModel.getString(2) + "\"" +
                            ", \"Id_equipo\" : \"" + generalModel.getInt(3) + "\"" +
                            ", \"Id_equipo2\" : \"" + generalModel.getInt(4) + "\"" +
                            ", \"Temperatura_a\" : \"" + generalModel.getString(5) + "\"" +
                            ", \"Temperatura_b\" : \"" + generalModel.getString(6) + "\"" +
                            ", \"Latitud\" : \"" + generalModel.getString(7) + "\"" +
                            ", \"Longitud\" : \"" + generalModel.getString(8) + "\"" +
                            ", \"Pendiente\" : \"" + generalModel.getString(9) + "\"" +
                            ", \"Criterio\" : \"" + generalModel.getString(10) + "\"" +
                            ", \"Supervisor\" : \"" + generalModel.getString(11) + "\"" +
                            "}"

                    listTemp.add(cont, jsonGeneral)
                    cont++
                    //Log.d("Lista Temp",listTemp.toString())
                } while (generalModel.moveToNext())
            }
            campoGenModel.addAll(listTemp)
            //llenar canceladas
            val canceladasModel = db.rawQuery(queryCanceladas, null)
            var listCanceladas: MutableList<String> = ArrayList()
            var contCanceladas:Int = 0
            if (canceladasModel.moveToFirst()){
                do {
                    var jsonCanceladas = "{" +
                            " \"Folio\" : \"" + canceladasModel.getString(1) + "\"" +
                            ",\"Id_solicitud\" : \"" + canceladasModel.getInt(2) + "\"" +
                            ",\"Muestra\" : \"" + canceladasModel.getInt(3) + "\"" +
                            ",\"Estado\" : \"" + canceladasModel.getInt(4) + "\"" +
                            "}"
                    listCanceladas.add(contCanceladas, jsonCanceladas)
                    contCanceladas++
                } while (canceladasModel.moveToNext())
            }
            canceladas.addAll(listCanceladas)


            //Llenar datos Punto
            var listPuntoTemp: MutableList<String> = ArrayList()
            val puntoModel = db.rawQuery(querySolGenerada, null)
            var contPunto: Int = 0
            if (puntoModel.moveToFirst()) {
                do {
                    //Log.d("Captura",generalModel.getString(2))
                    var jsonPunto = "{" +
                            " \"Punto\" : \"" + puntoModel.getString(17) + "\"" +
                            "}"

                    listPuntoTemp.add(contPunto, jsonPunto)
                    contPunto++
                    //Log.d("Lista Temp",listTemp.toString())
                } while (puntoModel.moveToNext())
            }
            solPuntoModel.addAll(listPuntoTemp)

            //Llenar datos evidencias
            var listTempEvi: MutableList<String> = ArrayList()
            val evidenciaModel = db.rawQuery(queryEvidencia, null)
            cont = 0
            if (evidenciaModel.moveToFirst()) {
                do {
                    //Log.d("Evidencia",evidenciaModel.getString(2))
                    var jsonEvidencia = "{" +
                            " \"FolioEvidencia\" : \"" + evidenciaModel.getString(1) + "\"" +
                            ", \"Codigo\" : \"" + evidenciaModel.getString(2) + "\"" +
                            "}"

                    listTempEvi.add(cont, jsonEvidencia)
                    cont++
                    //Log.d("Lista Temp",listTempEvi.toString())
                } while (evidenciaModel.moveToNext())
            }
            evidencia.addAll(listTempEvi)
            //Log.d("Lista Temp",evidencia.toString())


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
                            ", \"Lectura1\" : \"" + phTraModel.getString(3) + "\"" +
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
                            ", \"Lectura1\" : \"" + phCalModel.getString(3) + "\"" +
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
                            ", \"Lectura1\" : \"" + conTraModel.getString(3) + "\"" +
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
            val conCalModel = db.rawQuery(queryConCal, null)
            cont = 0
            if (conCalModel.moveToFirst()) {
                do {
                    //Log.d("Captura",generalModel.getString(2))
                    var jsonConCal = "{" +
                            " \"Id_solicitud\" : \"" + conCalModel.getInt(1) + "\"" +
                            ", \"Id_conCalidad\" : \"" + conCalModel.getString(2) + "\"" +
                            ", \"Lectura1\" : \"" + conCalModel.getString(3) + "\"" +
                            ", \"Lectura2\" : \"" + conCalModel.getString(4) + "\"" +
                            ", \"Lectura3\" : \"" + conCalModel.getString(5) + "\"" +
                            ", \"Estado\" : \"" + conCalModel.getString(6) + "\"" +
                            ", \"Promedio\" : \"" + conCalModel.getString(7) + "\"" +
                            "}"

                    listTempConC.add(cont, jsonConCal)
                    cont++
                    //Log.d("Lista Temp",listTemp.toString())
                } while (conCalModel.moveToNext())
            }
            conCalidad.addAll(listTempConC)

            var listTempPhM: MutableList<String> = ArrayList()
            val phMuestraModel = db.rawQuery(queryPhMuestra, null)
            cont = 0
            if (phMuestraModel.moveToFirst()) {
                do {
                    //Log.d("Captura",generalModel.getString(2))
                    var jsonPhMu = "{" +
                            " \"Id_solicitud\" : \"" + phMuestraModel.getInt(1) + "\"" +
                            ", \"Num_toma\" : \"" + phMuestraModel.getString(2) + "\"" +
                            ", \"Materia\" : \"" + phMuestraModel.getString(3) + "\"" +
                            ", \"Olor\" : \"" + phMuestraModel.getString(4) + "\"" +
                            ", \"Color\" : \"" + phMuestraModel.getString(5) + "\"" +
                            ", \"Ph1\" : \"" + phMuestraModel.getFloat(6) + "\"" +
                            ", \"Ph2\" : \"" + phMuestraModel.getString(7) + "\"" +
                            ", \"Ph3\" : \"" + phMuestraModel.getString(8) + "\"" +
                            ", \"Promedio\" : \"" + phMuestraModel.getString(9) + "\"" +
                            ", \"Fecha\" : \"" + phMuestraModel.getString(10) + "\"" +
                            ", \"Hora\" : \"" + phMuestraModel.getString(11) + "\"" +
                            "}"

                    listTempPhM.add(cont, jsonPhMu)
                    cont++
                    //Log.d("Lista Temp",listTemp.toString())
                } while (phMuestraModel.moveToNext())
            }
            phMuestra.addAll(listTempPhM)

            // LLenado de lista temperatura
            var listTempTempM: MutableList<String> = ArrayList()
            val tempMuestraModel = db.rawQuery(queryTempMuestra, null)
            cont = 0
            if (tempMuestraModel.moveToFirst()) {
                do {
                    //Log.d("Captura",generalModel.getString(2))
                    var jsonTempM = "{" +
                            " \"Id_solicitud\" : \"" + tempMuestraModel.getInt(1) + "\"" +
                            ", \"Num_toma\" : \"" + tempMuestraModel.getString(2) + "\"" +
                            ", \"Temp1\" : \"" + tempMuestraModel.getString(3) + "\"" +
                            ", \"Temp2\" : \"" + tempMuestraModel.getString(4) + "\"" +
                            ", \"Temp3\" : \"" + tempMuestraModel.getString(5) + "\"" +
                            ", \"Promedio\" : \"" + tempMuestraModel.getString(6) + "\"" +
                            "}"

                    listTempTempM.add(cont, jsonTempM)
                    cont++
                    //Log.d("Lista Temp",listTemp.toString())
                } while (tempMuestraModel.moveToNext())
            }
            tempMuestra.addAll(listTempTempM)
            // Llenado de temperatura ambiente
            var listTempAmbienteM: MutableList<String> = ArrayList()
            val tempAmbienteModel = db.rawQuery(queryTempAmbiente, null)
            cont = 0
            if (tempAmbienteModel.moveToFirst()) {
                do {
                    //Log.d("Captura",generalModel.getString(2))
                    var jsonAmbienteM = "{" +
                            " \"Id_solicitud\" : \"" + tempAmbienteModel.getInt(1) + "\"" +
                            ", \"Num_toma\" : \"" + tempAmbienteModel.getString(2) + "\"" +
                            ", \"TempA1\" : \"" + tempAmbienteModel.getString(3) + "\"" +
                            ", \"TempA2\" : \"" + tempAmbienteModel.getString(4) + "\"" +
                            ", \"TempA3\" : \"" + tempAmbienteModel.getString(5) + "\"" +
                            ", \"PromedioA\" : \"" + tempAmbienteModel.getString(6) + "\"" +
                            "}"

                    listTempAmbienteM.add(cont, jsonAmbienteM)
                    cont++
                    //Log.d("Lista Temp",listTemp.toString())
                } while (tempAmbienteModel.moveToNext())
            }
            tempAmbiente.addAll(listTempAmbienteM)
            //LLenado de conducitivdad de la muestra
            var listTempConM: MutableList<String> = ArrayList()
            val conMuestraModel = db.rawQuery(queryConMuestra, null)
            cont = 0
            if (conMuestraModel.moveToFirst()) {
                do {
                    //Log.d("Captura",generalModel.getString(2))
                    var jsonConM = "{" +
                            " \"Id_solicitud\" : \"" + conMuestraModel.getInt(1) + "\"" +
                            ", \"Num_toma\" : \"" + conMuestraModel.getString(2) + "\"" +
                            ", \"Conductividad1\" : \"" + conMuestraModel.getString(3) + "\"" +
                            ", \"Conductividad2\" : \"" + conMuestraModel.getString(4) + "\"" +
                            ", \"Conductividad3\" : \"" + conMuestraModel.getString(5) + "\"" +
                            ", \"Promedio\" : \"" + conMuestraModel.getString(6) + "\"" +
                            "}"

                    listTempConM.add(cont, jsonConM)
                    cont++
                    //Log.d("Lista Temp",listTemp.toString())
                } while (conMuestraModel.moveToNext())
            }
            conMuestra.addAll(listTempConM)

            var listTempGastoM: MutableList<String> = ArrayList()
            val gastoMuestraModel = db.rawQuery(queryGastoMuestra, null)
            cont = 0
            if (gastoMuestraModel.moveToFirst()) {
                do {
                    //Log.d("Captura",generalModel.getString(2))
                    var jsonGastoM = "{" +
                            " \"Id_solicitud\" : \"" + gastoMuestraModel.getInt(1) + "\"" +
                            ", \"Num_toma\" : \"" + gastoMuestraModel.getString(2) + "\"" +
                            ", \"Gasto1\" : \"" + gastoMuestraModel.getString(3) + "\"" +
                            ", \"Gasto2\" : \"" + gastoMuestraModel.getString(4) + "\"" +
                            ", \"Gasto3\" : \"" + gastoMuestraModel.getString(5) + "\"" +
                            ", \"Promedio\" : \"" + gastoMuestraModel.getString(6) + "\"" +
                            "}"

                    listTempGastoM.add(cont, jsonGastoM)
                    cont++
                    //Log.d("Lista Temp",listTemp.toString())
                } while (gastoMuestraModel.moveToNext())
            }
            gastoMuestra.addAll(listTempGastoM)

            //ph calidad muestra

            var lisTempPhCalidadMuestra: MutableList<String> =  ArrayList()
            val phcalidadmuestraModel = db.rawQuery(queryPhCalidadMuestra, null)
            cont = 0
            if (phcalidadmuestraModel.moveToFirst()){
                do {
                    var jsonPhCalidadMuestra = "{" +
                            " \"Id_solicitud\" : \"" + phcalidadmuestraModel.getInt(1) + "\"" +
                            ", \"Num_toma\" : \"" + phcalidadmuestraModel.getString(2) + "\"" +
                            ", \"Lectura1C\" : \"" + phcalidadmuestraModel.getString(3) + "\"" +
                            ", \"Lectura2C\" : \"" + phcalidadmuestraModel.getString(4) + "\"" +
                            ", \"Lectura3C\" : \"" + phcalidadmuestraModel.getString(5) + "\"" +
                            ", \"Estado\" : \"" + phcalidadmuestraModel.getString(6) + "\"" +
                            ", \"PromedioC\" : \"" + phcalidadmuestraModel.getString(7) + "\"" +
                            "}"
                    lisTempPhCalidadMuestra.add(cont, jsonPhCalidadMuestra)
                } while (phcalidadmuestraModel.moveToNext())
            }
            phCalidadMuestra.addAll(lisTempPhCalidadMuestra)
            //llenar datos compuestos
            var listTempCampoCompuesto: MutableList<String> = ArrayList()
            val compuestoModel  =  db.rawQuery(queryCampoCompuesto, null)
            cont = 0
            if (compuestoModel.moveToFirst()){
                do {
                    var jsonCampoCompuesto = "{" +
                            " \"Id_solicitud\" : \"" + compuestoModel.getInt(1) + "\"" +
                            ", \"Metodo_aforo\" : \"" + compuestoModel.getInt(2) + "\"" +
                            ", \"Con_tratamiento\" : \"" + compuestoModel.getString(3) + "\"" +
                            ", \"Tipo_tratamiento\" : \"" + compuestoModel.getInt(4) + "\"" +
                            ", \"Proc_muestreo\" : \"" + compuestoModel.getInt(5) + "\"" +
                            ", \"Observaciones\" : \"" + compuestoModel.getString(6) + "\"" +
                            ", \"Obser_solicitud\" : \"" + compuestoModel.getString(7) + "\"" +
                            ", \"Ph_muestraComp\" : \"" + compuestoModel.getString(8) + "\"" +
                            ", \"Temp_muestraComp\" : \"" + compuestoModel.getString(9) + "\"" +
                            ", \"Volumen_calculado\" : \"" + compuestoModel.getString(10) + "\"" +
                            ", \"Cloruros\" : \"" + compuestoModel.getString(11) + "\"" +
                            "}"

                    listTempCampoCompuesto.add(cont, jsonCampoCompuesto)
                    cont++

                } while (compuestoModel.moveToNext())
            }
            campoCompuesto.addAll(listTempCampoCompuesto)



            val stringRequest = object : StringRequest(
                Request.Method.POST, UserApplication.prefs.BASE_URL + "enviarDatos",
                Response.Listener<String> { response ->
                    try {
                        val obj = JSONObject(response)
                        Log.d("Response", response)
                        if (obj.getBoolean("response") == true) {
                          //  Log.d("datos", obj.getString("dato"))

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
                    Toast.makeText(applicationContext, "Error. Es probable que falte información" + volleyError.message, Toast.LENGTH_LONG)
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
                    params.put("phMuestra", phMuestra.toString())
                    params.put("tempMuestra", tempMuestra.toString())
                    params.put("tempAmbiente", tempAmbiente.toString())
                    params.put("conMuestra", conMuestra.toString())
                    params.put("gastoMuestra", gastoMuestra.toString())
                    params.put("campoCompuesto", campoCompuesto.toString())
                    params.put("phCalidadMuestra", phCalidadMuestra.toString())
                    params.put("idMuestreador", UserApplication.prefs.getMuestreadorId())
                    params.put("evidencia",evidencia.toString())
                    params.put("folio", folio)
                    params.put("solPunto",solPuntoModel.toString())
                    params.put("canceladas", listCanceladas.toString())

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
        var listaTermometro1 = JSONArray(data.getString("pc100"))
        var listaTermometro2 = JSONArray(data.getString("hanna"))
        var listaConTrazable = JSONArray(data.getString("conTrazable"))
        var listaConCalidad = JSONArray(data.getString("conCalidad"))
        var listaColor = JSONArray(data.getString("modelColor"))
        var listAforo = JSONArray(data.getString("modelAforo"))
        var listTipoTratamiento = JSONArray(data.getString("modelTipo"))
        var listConTratamiento = JSONArray(data.getString("modelConTratamiento"))
        //var listCampoCompuestos = JSONArray(data.getString("CampoCompuestos"))

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
                    muestreo.getInt("Id_direccion"),
                    muestreo.getInt("Id_contacto"),
                    muestreo.getString("Observacion"),
                    muestreo.getString("Servicio"),
                    muestreo.getString("Descarga"),
                    muestreo.getString("Clave"),
                    muestreo.getString("Fecha_muestreo"),
                    muestreo.getString("Num_tomas"),
                    muestreo.getInt("Id_muestreador"),
                    1,
                    muestreo.getString("Punto"),
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
                        0,
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                    )
                    var db = DataBaseHandler(this)
                    db.insertGeneral(generalModel)
                    // Creat PhCalidad Muestra
                    var phCalModel = PhCalidad(
                        idSolGen,
                        0,
                        0,
                        "",
                        "",
                        "",
                        "",
                        "",

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
                    var compuestosModel = CampoCompuesto(
                        idSolGen,
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                    )
                    db.insertCampoCompuesto(compuestosModel)

                } while (solGenModel2.moveToNext())
            }

        }
        //Sincronizar datos termometro1 pc100
        for (i in 0 until listaTermometro1.length()) {
            val termometro = listaTermometro1.getJSONObject(i)
            val id = termometro.getString("Id_termometro").toString()
            val queryTermo = "SELECT * FROM TermometroCampo WHERE Id_termometro = '$id'"
            val termometroCampo = db.rawQuery(queryTermo, null)
            var cont: Int = 0
            if (termometroCampo.moveToFirst()) {

            } else {
                var termometroModel = TermometroCampo(
                    termometro.getString("Id_termometro").toInt(),
                    termometro.getString("Id_muestreador").toInt(),
                    termometro.getString("Equipo"),
                    termometro.getString("Marca"),
                    termometro.getString("Modelo"),
                    termometro.getString("Serie"),
                )
                var db = DataBaseHandler(this)
                db.insertTermometroCampo(termometroModel)
            }
        }
        // termometros hanna
        for (i in 0 until listaTermometro2.length()) {
            val termometro = listaTermometro2.getJSONObject(i)
            val id = termometro.getString("Id_termometro").toString()
            val queryTermo = "SELECT * FROM TermometroCampo WHERE Id_termometro = '$id'"
            val termometroCampo = db.rawQuery(queryTermo, null)
            var cont: Int = 0
            if (termometroCampo.moveToFirst()) {

            } else {
                var termometroModel2 = TermometroCampo2(
                    termometro.getString("Id_termometro").toInt(),
                    termometro.getString("Id_muestreador").toInt(),
                    termometro.getString("Equipo"),
                    termometro.getString("Marca"),
                    termometro.getString("Modelo"),
                    termometro.getString("Serie"),
                )
                var db = DataBaseHandler(this)
                db.insertTermometroCampo2(termometroModel2)
            }
        }
        //sincronizar Color
        for (i in 0 until listaColor.length()){
            val color = listaColor.getJSONObject(i)
            val nombre = color.getString("Color").toString()
            val queryColor = "SELECT * FROM color WHERE Color = '$nombre'"
            val colorCatalogo = db.rawQuery(queryColor, null)

            if (colorCatalogo.moveToFirst()) {
                do {

                } while (colorCatalogo.moveToNext())
            } else {
                var colorModel = Color(
                    color.getString("Color"),
                )
                var db = DataBaseHandler(this)
                db.insertColor(colorModel)
            }
        }
        //sincronizar Aforo
        for (i in 0 until listAforo.length()){
            val aforo = listAforo.getJSONObject(i)
            val nombre = aforo.getString("Aforo").toString()
            val queryAforo = "SELECT * FROM aforo WHERE Aforo = '$nombre'"
            val AforoCatalogo = db.rawQuery(queryAforo, null)

            if (AforoCatalogo.moveToFirst()) {
                do {

                } while (AforoCatalogo.moveToNext())
            } else {
                var aforoModel = MetodoAforo(
                    aforo.getString("Aforo"),
                )
                var db = DataBaseHandler(this)
                db.insertAforo(aforoModel)
            }
        }

        for (i in 0 until listTipoTratamiento.length()){
            val tipo = listTipoTratamiento.getJSONObject(i)
            val tratamiento = tipo.getString("Tratamiento").toString()
            val queryTipo = "SELECT * FROM tipo_tratamiento WHERE Tratamiento = '$tratamiento'"
            val tipoCatalogo = db.rawQuery(queryTipo, null)

            if (tipoCatalogo.moveToFirst()) {
                do {

                } while (tipoCatalogo.moveToNext())
            } else {
                var tipoModel = TipoTratamiento(
                    tipo.getString("Tratamiento"),
                )
                var db = DataBaseHandler(this)
                db.insertTipoTratamiento(tipoModel)
            }
        }
        //con tratamiento
        for (i in 0 until listConTratamiento.length()){
            val con = listConTratamiento.getJSONObject(i)
            val conTratamiento = con.getString("Tratamiento").toString()
            val queryCon = "SELECT * FROM con_tratamiento WHERE ConTratamiento = '$conTratamiento'"
            val ConCatalogo = db.rawQuery(queryCon, null)

            if (ConCatalogo.moveToFirst()) {
                do {

                } while (ConCatalogo.moveToNext())
            } else {
                var conModel = ConTratamiento(
                    con.getString("Tratamiento"),
                )
                var db = DataBaseHandler(this)
                db.insertConTratamiento(conModel)
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