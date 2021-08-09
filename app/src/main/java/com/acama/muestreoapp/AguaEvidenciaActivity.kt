package com.acama.muestreoapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.acama.muestreoapp.databinding.ActivityAguaEvidenciaBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_agua_evidencia.*
import java.util.HashMap

class AguaEvidenciaActivity : AppCompatActivity() {

    private val SELECT_ACTIVITY = 50
    private var imageUri: Uri? = null

    private val File = 1
    private val database = Firebase.database
    val myRef = database.getReference("user")

    private lateinit var bin: ActivityAguaEvidenciaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityAguaEvidenciaBinding.inflate(layoutInflater)
        setContentView(bin.root)


        bin.imgRegresar.setOnClickListener(View.OnClickListener { v: View? ->
            DialogVolver()
        })
        bin.img1.setOnClickListener{
           //ImageController.selectPhotoFromGallery(this, SELECT_ACTIVITY)
            fileUpload()
        }
        bin.btHecho.setOnClickListener{
            onBackPressed()
        }
    }
    fun fileUpload() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, File)
    }
    fun DialogVolver(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Cuidado")
        builder.setMessage("Los datos capturados se perderan ¿Seguro que quieres salir?")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            Toast.makeText(applicationContext,
                android.R.string.yes, Toast.LENGTH_SHORT).show()
            onBackPressed()
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            Toast.makeText(applicationContext,
                android.R.string.no, Toast.LENGTH_SHORT).show()
        }
        builder.show()

    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
////        super.onActivityResult(requestCode, resultCode, data)
////
////        when {
////            requestCode == SELECT_ACTIVITY && resultCode  == Activity.RESULT_OK -> {
////                imageUri = data!!.data
////
////               bin.img1.setImageURI(imageUri)
////
////            }
////        }
////    }
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == File) {
        if (resultCode == RESULT_OK) {
            val FileUri = data!!.data
            val Folder: StorageReference =
                FirebaseStorage.getInstance().getReference().child("Campo")
            val file_name: StorageReference = Folder.child("file" + FileUri!!.lastPathSegment)
            file_name.putFile(FileUri).addOnSuccessListener { taskSnapshot ->
                file_name.getDownloadUrl().addOnSuccessListener { uri ->
                    val hashMap =
                        HashMap<String, String>()
                    hashMap["link"] = java.lang.String.valueOf(uri)
                    myRef.setValue(hashMap)
                    Log.d("Mensaje", "Se subió correctamente")
                    Toast.makeText(this, "Se subió correctamente", Toast.LENGTH_SHORT).show()
                    bin.img1.setImageURI(FileUri)
                }
            }
        }
    }
}
}