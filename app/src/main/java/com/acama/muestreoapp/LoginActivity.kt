package com.acama.muestreoapp

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import com.acama.muestreoapp.api.VolleySingleton

import com.acama.muestreoapp.databinding.ActivityLoginBinding
import com.acama.muestreoapp.models.SolicitudGenerada
import com.acama.muestreoapp.models.Usuarios
import com.acama.muestreoapp.preference.Prefs
import com.acama.muestreoapp.preference.UserApplication.Companion.prefs
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private lateinit var bin: ActivityLoginBinding
    private lateinit var con: DataBaseHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this
        bin = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bin.root)

        if (prefs.getPermanecerConectado()){
            changeActivity()
        }else{
            con = DataBaseHandler(this)
            bin.btnEntrar.setOnClickListener {
                checkfirstOpen()
            }
        }
    }

    fun changeActivity(){
         val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }

    fun checkfirstOpen(){
        if(prefs.getFirstStart()){
            Log.d("checkFirstOpen","Inicio por segunda vez")
            loginLocal(bin.edtUser.text.toString(),bin.edtPassword.text.toString())
            //changeActivity()
        }else{
            Log.d("checkFirstOpen","Inicio por primera vez")
            loginApi(bin.edtUser.text.toString(),bin.edtPassword.text.toString())
            //changeActivity()
        }
    }

    fun loginLocal(user:String,pass:String){
        val db : SQLiteDatabase = con.readableDatabase
        val query = "SELECT * FROM usuarios_app WHERE User = '$user' AND UserPass = '$pass'"
        val users = db.rawQuery(query,null)
        if (users.moveToFirst()){
            do{
                Log.d("User",users.getString(1))
                prefs.saveMuestreador(users.getString(1),users.getString(2))
                prefs.savePermanecerConectado(bin.ckdConectado.isChecked)
            }while (users.moveToNext())
            Toast.makeText(this,"Sesión satisfactoria",Toast.LENGTH_SHORT).show()
            changeActivity()
        }else{
            Toast.makeText(this,"Usuario y/o contraseña incorrecto",Toast.LENGTH_SHORT).show()
        }
    }


    fun loginApi(user:String,pass:String) {
       CoroutineScope(Dispatchers.IO).launch {
           val stringRequest = object : StringRequest(
               Request.Method.POST, prefs.BASE_URL + "login",
               Response.Listener<String> { response ->
                   try {
                       val obj = JSONObject(response)
                       Log.d("Response",response)
                       if (obj.getBoolean("response") == true){
                           syncFirstData(obj)
                           prefs.saveFirstStart(true)
                           prefs.savePermanecerConectado(bin.ckdConectado.isChecked)
                           Toast.makeText(applicationContext, "Sesión satisfactoria", Toast.LENGTH_SHORT).show()
                           changeActivity()
                       }else{
                           Toast.makeText(applicationContext, "Usuario y/o contraseña incorrecta", Toast.LENGTH_SHORT).show()
                       }
                   } catch (e: JSONException) {
                       e.printStackTrace()
                       Toast.makeText(applicationContext, "Error en la solicitud", Toast.LENGTH_SHORT).show()
                   }

               },
               Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message + " Sin Response", Toast.LENGTH_SHORT).show() }) {
               @Throws(AuthFailureError::class)
               override fun getParams(): Map<String, String> {
                   val params = HashMap<String, String>()
                   params.put("user", user)
                   params.put("pass", pass)
                   return params
               }
           }
           // Agregamos el request para que nos permita retornar y/o visualizar la respuesta
           VolleySingleton.getInstance(this@LoginActivity).addToRequestQueue(stringRequest)
       }
    }

    fun syncFirstData(data:JSONObject) {
        //Log.d("syncFirstData",data.getString("usuarios"))
        var user_array = JSONArray(data.getString("usuarios"))
        //Log.d("jsonArray",json_array[0].toString())
        var db = DataBaseHandler(this)


        var tempUser = JSONArray(data.getString("data"))
        var temp = tempUser.getJSONObject(0)
        prefs.saveMuestreador(temp.getString("Id_muestreador"),temp.getString("User"))
        for (i in 0 until user_array.length()){
            /* var jsonObject = json_array.getJSONObject(i)
            Log.d("User",jsonObject.getString("User"))*/
                var users = user_array.getJSONObject(i)
            var usuariosModel = Usuarios(
                users.getString("Id_muestreador"),
                users.getString("User"),
                users.getString("UserPass")
            )
            db.inserUsuario(usuariosModel)
        }

       /*var listaMuestreo = JSONArray(data.getString("solicitudes"))

        for(i in 0 until listaMuestreo.length()){
            var muestreo = listaMuestreo.getJSONObject(i)
            Log.d("Solicitud",muestreo.getInt("Id_solicitud").toString())
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
            db.insertSolicitudGenerada(muestreoModel)
        }*/


    }

}
