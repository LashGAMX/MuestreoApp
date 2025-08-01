package com.acama.muestreoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.Toast
import com.acama.muestreoapp.databinding.ActivityMuestreosBinding

class MuestreosActivity : AppCompatActivity() {

    private lateinit var bin: ActivityMuestreosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityMuestreosBinding.inflate(layoutInflater)
        setContentView(bin.root)

        val muestreos = arrayOf("14-9/21\n"+"AB CALSA, S.A DE C.V","147-18/21\n"+"LABORATORIO ACAMA",
        "13-14/12\n"+"ESTACION DE SERVICIO CAPRICORNIO S.A")

        val adaptador1 = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, muestreos)
        bin.lstMuestreos.adapter = adaptador1
        
        bin.lstMuestreos.setOnItemClickListener { adapterView, view, i, l ->
            //acción que cada elemen to debe llevar

        }
        registerForContextMenu(bin.lstMuestreos)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.menu_lista, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.enviar -> {
                Toast.makeText(applicationContext, "Enviar", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.info -> {
                Toast.makeText(applicationContext, "info", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.firma -> {
                Toast.makeText(applicationContext, "Firma", Toast.LENGTH_SHORT).show()
                return true
            }
        }

        return super.onContextItemSelected(item)
    }
}