package com.example.holafirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       val database = Firebase.database.reference
        val listener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val data = snapshot.getValue(String::class.java)
                    findViewById<TextView>(R.id.tvHolaFB).text = "Firebase remote: $data"
                } else {

                    findViewById<TextView>(R.id.tvHolaFB).text = "Ruta sin datos."
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Error al leer datos", Toast.LENGTH_SHORT).show()
            }
        }

        val dataRef = database.child("hola_firebase").child("data")
        dataRef.addValueEventListener(listener)
        findViewById<ImageButton>(R.id.btnAdd).setOnClickListener {
            val data2 = findViewById<TextInputEditText>(R.id.etData).text.toString()
            dataRef.setValue(data2)
                .addOnSuccessListener {
                    Toast.makeText(this@MainActivity, "Insercción exitosa", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this@MainActivity, "Error al insertar datos", Toast.LENGTH_SHORT).show()
                }
                .addOnCompleteListener{
                    Toast.makeText(this@MainActivity, "Operación completada", Toast.LENGTH_SHORT).show()
                }
            findViewById<TextInputEditText>(R.id.etData).text?.clear()
        }
        findViewById<ImageButton>(R.id.btnAdd).setOnLongClickListener{
            dataRef.removeValue()
            true
        }
    }
}