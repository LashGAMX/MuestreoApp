package com.acama.muestreoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.acama.muestreoapp.databinding.ActivityAguaCapturaBinding

class AguaCapturaActivity : AppCompatActivity() {
    private  lateinit var bin: ActivityAguaCapturaBinding
    private lateinit var folio:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityAguaCapturaBinding.inflate(layoutInflater)
        setContentView(bin.root)

            getExtras()

            bin.btnGenerales.setOnClickListener(View.OnClickListener { v: View? ->
                val intent = Intent(this, AguaGeneralesActivity::class.java)
                intent.putExtra("folio",folio)
                startActivity(intent)
            })

            bin.btnMuestreo.setOnClickListener(View.OnClickListener { v: View? ->
                val intent = Intent(this,AguaDatosMuestreosActivity::class.java)
                intent.putExtra("folio",folio)
                startActivity(intent)
            })
            bin.btnCompuesto.setOnClickListener(View.OnClickListener { v: View? ->
                val intent = Intent(this,AguaCompuestosActivity::class.java)
                startActivity(intent)
            })
            bin.btnEvidencia.setOnClickListener(View.OnClickListener { v: View? ->
                val intent = Intent(this,AguaEvidenciaActivity::class.java)
                startActivity(intent)
            })
            bin.imgRegresar.setOnClickListener(View.OnClickListener { v: View? ->
                //val intent = Intent(this,ListaAguaActivity::class.java)
                //startActivity(intent)
                //finish()
                onBackPressed()
            })
    }
    fun getExtras(){
        val bundle = intent.extras
        val fol = bundle?.get("folio")
        folio = fol.toString()
        Toast.makeText(this,folio,Toast.LENGTH_SHORT).show()

        bin.txtFolio.text = folio
    }

}