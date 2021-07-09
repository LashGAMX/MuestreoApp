package com.acama.muestreoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import com.acama.muestreoapp.api.VolleySingleton

import com.acama.muestreoapp.databinding.ActivityLoginBinding
import com.acama.muestreoapp.preference.Prefs
import com.acama.muestreoapp.preference.UserApplication.Companion.prefs
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private lateinit var bin: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bin = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bin.root)



        bin.btnEntrar.setOnClickListener {
            //loginApi()
            loginApi(bin.edtUser.text.toString(),bin.edtPassword.text.toString())
            //Toast.makeText(applicationContext, bin.edtPassword.text, Toast.LENGTH_LONG).show()
            // val intent = Intent(this, MenuActivity::class.java)
            //startActivity(intent)

        }

        //checkfirstOpen()
    }

    fun checkfirstOpen(){
        if(prefs.getFirstStart()){
            Toast.makeText(this,"Segunda",Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this,"Inicio por primera vez",Toast.LENGTH_LONG).show()
        }
    }

    fun loginApi(user:String,pass:String) {
        Log.d("User",user)
        Log.d("Pass",pass)
        val stringRequest = object : StringRequest(
                Request.Method.POST, prefs.BASE_URL + "login",
                Response.Listener<String> { response ->
                    try {
                        val obj = JSONObject(response)
                        Log.d("Response",response)
                        Toast.makeText(applicationContext, "Sesión satisfactoria", Toast.LENGTH_LONG).show()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(applicationContext, "Usuario y/o contraseña incorrecta", Toast.LENGTH_LONG).show()
                    }

                },
                Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("user", user)
                params.put("pass", pass)
                return params
            }
        }
        // Agregamos el request para que nos permita retornar y/o visualizar la respuesta

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
    }



}
