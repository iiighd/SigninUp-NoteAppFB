package com.example.noteappfb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class notePage : AppCompatActivity() {
    lateinit var mcreateNotefab : FloatingActionButton
    lateinit var mlogoutfab : FloatingActionButton
    lateinit var firebaseAuth : FirebaseAuth
    lateinit var mrecyclerView: RecyclerView
    lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager
    lateinit var firebaseUser: FirebaseUser
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var noteAdapter : FirestoreRecyclerAdapter<firebasemodel,NoteViewHolder>
    private lateinit var allusernotes : FirestoreRecyclerOptions<firebasemodel>





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_page)
        firebaseAuth = FirebaseAuth.getInstance()
        supportActionBar?.setTitle("All Notes ")

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        firebaseFirestore = FirebaseFirestore.getInstance()

        mcreateNotefab = findViewById(R.id.createNotefab)
        mcreateNotefab.setOnClickListener {
            intent = Intent(this@notePage , createNote::class.java)
            startActivity(intent)
        }
        mlogoutfab = findViewById(R.id.logoutfab)
        mlogoutfab.setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent(this@notePage , MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val query: Query = firebaseFirestore.collection("notes").document(firebaseUser.uid).collection("myNotes").orderBy("title", Query.Direction.ASCENDING)
        allusernotes = FirestoreRecyclerOptions.Builder<firebasemodel>().setQuery(query,firebasemodel::class.java).build()




     class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val notetitle: TextView
        private val notecontent: TextView
        var mnote: LinearLayout

        init {
            notetitle = itemView.findViewById(R.id.notetitle)
            notecontent = itemView.findViewById(R.id.notecontant)
            mnote = itemView.findViewById(R.id.note)
        }
    }
}

