package com.acama.muestreoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.acama.muestreoapp.api.VolleySingleton
import com.acama.muestreoapp.databinding.ActivityMenuBinding
import com.acama.muestreoapp.databinding.ActivityMuestreosBinding
import com.acama.muestreoapp.preference.UserApplication
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

class MenuActivity : AppCompatActivity() {

    private lateinit var bin: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(bin.root)

        var user = UserApplication.prefs.getMuestreador()
        Toast.makeText(this,"Bienvenido $user" ,Toast.LENGTH_LONG).show()

        bin.cardAgua.setOnClickListener(View.OnClickListener { v: View? ->

            val intent = Intent(this, ListaAguaActivity::class.java)
            startActivity(intent)
        })
        bin.btnCerrar.setOnClickListener {
            UserApplication.prefs.savePermanecerConectado(false)
            finish()
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }
    fun verificacionVer(data: JSONObject){

    }
    fun version(){
        CoroutineScope(Dispatchers.IO).launch {
           val stringRequest = object : StringRequest(
               Request.Method.POST, UserApplication.prefs.BASE_URL + "version",
               Response.Listener<String> { response ->
                   try {
                       val obj = JSONObject(response)
                       Log.d("Response", response)
                       if (obj.getBoolean("response") == true) {
                           Log.d("datos", obj.getString("datos"))
                           //Log.d("ds",obj.getString("ds"))
                           verificacionVer(obj)

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
               }){

           }
            VolleySingleton.getInstance(this@MenuActivity).addToRequestQueue(stringRequest)
        }
    }
}