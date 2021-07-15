package com.acama.muestreoapp.agua

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MuestraSimpleAdapter (val muestrasimple: List<MuestraSimple>) : RecyclerView.Adapter<MuestraSimpleAdapter.MuestraSimpleHolder>() {

    class MuestraSimpleHolder(val view: View):RecyclerView.ViewHolder(view){

        private val bin = Item

    }

}