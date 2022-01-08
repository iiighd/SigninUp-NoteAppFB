package com.example.noteappfb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.HashMap

class createNote : AppCompatActivity() {
    lateinit var msavenotefab : FloatingActionButton
    lateinit var mcreatenotetitle : EditText
    lateinit var mcreatenotedet : EditText
    lateinit var toolbar: android.widget.Toolbar
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var documentReference : DocumentReference
    lateinit var notes : Map<String,Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

        msavenotefab = findViewById(R.id.saveNotefab)
        mcreatenotetitle = findViewById(R.id.createtitlenote)
        mcreatenotedet = findViewById(R.id.createnotedet)
         toolbar = findViewById(R.id.toolbarnote)
//        setSupportActionBar(this.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()

        msavenotefab.setOnClickListener {
            val title = mcreatenotetitle.text.toString()
            val desc = mcreatenotedet.text.toString()
            if (title.isEmpty() || desc.isEmpty()){
                Toast.makeText(this , "all field are required ", Toast.LENGTH_SHORT).show()
            }else {
                documentReference = firebaseFirestore.collection("notes").document(firebaseUser.uid).collection("myNotes").document()
                notes = HashMap<String,Any>()
                (notes as HashMap<String, Any>).put("title", title)
                (notes as HashMap<String, Any>).put("content", desc)

                documentReference.set(notes).addOnSuccessListener {
                    Toast.makeText(this , "note Created Successfuly ", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@createNote , notePage::class.java)
                }
                documentReference.set(notes).addOnFailureListener{
                    Toast.makeText(this , "Failed to Create note ", Toast.LENGTH_SHORT).show()
                }

            }

        }

    }


}