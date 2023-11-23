package com.example.firebasecrudapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var myRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = Firebase.database
        myRef = database.getReference("message")
        myRef.setValue("My First Realtime Database App")

        val btn1 = findViewById<Button>(R.id.btn1)
        val text1 = findViewById<TextView>(R.id.text1)

        btn1.setOnClickListener(this)
        // Odczyt danych
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val value = dataSnapshot.getValue()
                text1.setText("Pobrano tekst: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Status", "Błąd pobrania danych.", error.toException())
            }
        })

    }

    override fun onClick(v: View?) {
        val edt1 = findViewById<EditText>(R.id.edt1)
        when (v!!.id) {
            R.id.btn1 -> {
                myRef.setValue(edt1.text.toString())
            }
        }
    }
}