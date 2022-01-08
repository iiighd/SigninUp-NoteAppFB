package com.example.noteappfb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    lateinit var loginBtn : RelativeLayout
    lateinit var emailEtlogin : EditText
    lateinit var passwordEtlogin : EditText
    lateinit var gotosignin : RelativeLayout
    lateinit var firebaseAuth : FirebaseAuth
    lateinit var firebaseUser : FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        loginBtn = findViewById(R.id.login)
        emailEtlogin = findViewById(R.id.loginemail)
        passwordEtlogin = findViewById(R.id.loginpassword)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = firebaseAuth.currentUser!!
        if (firebaseUser != null){
            finish()
            val intent = Intent(this@MainActivity , notePage::class.java)
            startActivity(intent)
        }


        gotosignin = findViewById(R.id.gotosignup)
        gotosignin.setOnClickListener {
            val intent = Intent(this@MainActivity , Signup::class.java)
            startActivity(intent)
        }

        loginBtn.setOnClickListener {
            val email = emailEtlogin.text.toString().trim()
            val password = passwordEtlogin.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(this , "all field are required to complete log in ", Toast.LENGTH_SHORT).show()
            } else {
                //log in user
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    if(it.isSuccessful){
                        checkemail()
                    } else{
                    Toast.makeText(this , "email is not exist",Toast.LENGTH_SHORT).show()}

                }
            }
        }
    }

    private fun checkemail() {
        firebaseUser = firebaseAuth.currentUser!!

        if (firebaseUser.isEmailVerified == true){
            Toast.makeText(this, "hello ", Toast.LENGTH_SHORT).show()
            finish()
            val intent = Intent(this@MainActivity , notePage::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this , "not verified email , please check you email and try again  ", Toast.LENGTH_SHORT).show()
            firebaseAuth.signOut()
        }
    }
}