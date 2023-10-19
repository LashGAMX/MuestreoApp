package com.acama.muestreoapp

import android.Manifest
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.acama.muestreoapp.api.VolleySingleton
import com.acama.muestreoapp.databinding.ActivityMenuBinding
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
        Manifest.permission.WRITE_EXTERNAL_STORAGE
        bin = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(bin.root)
        version()

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
        bin.txtVersion.setOnClickListener {
            version()
        }

    }

    fun verificacionVer(version: String, name: String){

        var txt = bin.txtVersion.text
        var arr = txt.split(" ")
        var versionActual = arr[1]

        if (version.toString() == versionActual.toString()){
          //  DialogNoVersion()
        } else {
            DialogVersion(name)
        }

    }
    fun version(){
        CoroutineScope(Dispatchers.IO).launch {
           val stringRequest = object : StringRequest(
               Request.Method.POST, UserApplication.prefs.BASE_URL + "version",
               Response.Listener<String> { response ->
                   try {
                       val obj = JSONObject(response)
                       Log.d("Response", response)
                       var version = obj.getString("version")
                       var name = obj.getString("name")
                       verificacionVer(version, name)
                       if (obj.getBoolean("response") == true) {
                           Log.d("datos", obj.getString("datos"))

                           //verificacionVer(version)

                       } else {
                           Toast.makeText(
                               applicationContext,
                               "Error en obtener version",
                               Toast.LENGTH_LONG
                           ).show()
                       }
                   } catch (e: JSONException) {
                       e.printStackTrace()
                       Toast.makeText(
                           applicationContext,
                           "",
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
    fun DialogVersion(name: String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Nueva Actualización disponible")
        builder.setMessage("Descarga la nueva versión")
        builder.setPositiveButton(android.R.string.ok) { dialog, which ->
            descargar(name)
        }
        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            //CANCEL
        }
        builder.show()
    }
    fun descargar(name: String){
        val url = "https://techcrunch.com/wp-content/uploads/2015/04/codecode.jpg"
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("File")
            .setDescription("Descargando...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
        val dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        dm.enqueue(request)
    }

}