 package com.acama.muestreoapp

import android.R
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.acama.muestreoapp.databinding.ActivityListaAguaBinding
import com.acama.muestreoapp.preference.UserApplication


 class ListaAguaActivity : AppCompatActivity() {
    private lateinit var bin:  ActivityListaAguaBinding
    private lateinit var con: DataBaseHandler
            override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
                bin = ActivityListaAguaBinding.inflate(layoutInflater)
                setContentView(bin.root)

                con = DataBaseHandler(this)

                //Mostrar datos en la lista
                val muestreos = arrayOf("14-9/21\n"+"AB CALSA, S.A DE C.V","147-18/21\n"+"LABORATORIO ACAMA",
                    "13-14/12\n"+"ESTACION DE SERVICIO CAPRICORNIO S.A")

                val listaMuestreo:MutableList<String> = mutableListOf()


                val db : SQLiteDatabase = con.readableDatabase
                val idMuestreador = UserApplication.prefs.getMuestreadorId()
                val query = "SELECT * FROM solicitud_generadas WHERE Id_muestreador = '$idMuestreador'"
                val muestreoModel = db.rawQuery(query,null)
                if (muestreoModel.moveToFirst()){
                    do{
                     //   Log.d("User",users.getString(1))
                        //UserApplication.prefs.saveMuestreador(users.getString(1),users.getString(2))
                        listaMuestreo.add(""+muestreoModel.getString(1)+"\n"+muestreoModel.getString(6))
                    }while (muestreoModel.moveToNext())
                   // Toast.makeText(this,"Sesión satisfactoria",Toast.LENGTH_SHORT).show()
                }else{
                 //   Toast.makeText(this,"Usuario y/o contraseña incorrecto",Toast.LENGTH_SHORT).show()
                }

                val adaptador1 = ArrayAdapter<String>(this, R.layout.simple_list_item_1, listaMuestreo)
                bin.lstMuestreos.adapter = adaptador1

                bin.lstMuestreos.setOnItemClickListener { adapterView, view, i, l ->
                    //acción que cada elemen to debe llevar
                    val intent = Intent(this, AguaCapturaActivity::class.java)
                    startActivity(intent)

                }
                registerForContextMenu(bin.lstMuestreos)

                /*
                val dbr : SQLiteDatabase = con.writableDatabase
                bin.imgSincronizar.setOnClickListener { con.deleteData(dbr) }
                admin
                 */
    }
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(com.acama.muestreoapp.R.menu.menu_lista, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            com.acama.muestreoapp.R.id.enviar -> {
                Toast.makeText(applicationContext, "Enviar", Toast.LENGTH_SHORT).show()
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

     fun sycnDatosMuestreo(){

     }

}