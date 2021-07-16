package com.acama.muestreoapp.agua

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.acama.muestreoapp.R
import com.acama.muestreoapp.databinding.ItemMuestrasimpleBinding

class MuestraSimpleAdapter (val muestrasimple: List<MuestraSimple>) : RecyclerView.Adapter<MuestraSimpleAdapter.MuestraSimpleHolder>() {

    class MuestraSimpleHolder(val view: View):RecyclerView.ViewHolder(view){

        private val bin = ItemMuestrasimpleBinding.bind(view)
        fun render(muestrasimple:MuestraSimple){
            bin.txtMuestra.text = "Muestra simple"
            bin.txtNumeroMuestra.text = muestrasimple.numToma.toString()
            //bin.edtColor.setBackgroundColor(R.drawable._11_computer)
            view.setOnClickListener { Toast.makeText(view.context,"Has seleccionado ${muestrasimple.numToma}",Toast.LENGTH_SHORT).show() }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MuestraSimpleHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
       return MuestraSimpleHolder(layoutInflater.inflate(R.layout.item_muestrasimple,parent,false))

    }

    override fun onBindViewHolder(holder: MuestraSimpleHolder, position: Int) {
        holder.render(muestrasimple[position])
    }

    override fun getItemCount(): Int  = muestrasimple.size


}