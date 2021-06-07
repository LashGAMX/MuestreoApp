package com.acama.muestreoapp

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.acama.muestreoapp.databinding.ActivityListaAguaBinding


class ListaAguaActivity : AppCompatActivity() {
    private lateinit var bin:  ActivityListaAguaBinding
            override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
                bin = ActivityListaAguaBinding.inflate(layoutInflater)
                setContentView(bin.root)

                //Mostrar datos en la lista
                val muestreos = arrayOf("14-9/21\n"+"AB CALSA, S.A DE C.V","147-18/21\n"+"LABORATORIO ACAMA",
                    "13-14/12\n"+"ESTACION DE SERVICIO CAPRICORNIO S.A")

                val adaptador1 = ArrayAdapter<String>(this, R.layout.simple_list_item_1, muestreos)
                bin.lstMuestreos.adapter = adaptador1

                bin.lstMuestreos.setOnItemClickListener { adapterView, view, i, l ->
                    //acción que cada elemen to debe llevar
                    val intent = Intent(this, AguaCapturaActivity::class.java)
                    startActivity(intent)

                }
                registerForContextMenu(bin.lstMuestreos)
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

}