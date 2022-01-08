package com.example.noteappfb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase

class Signup : AppCompatActivity() {
    lateinit var emailETsignup : EditText
    lateinit var passwordETsignup : EditText
    lateinit var loginBtn : RelativeLayout
    lateinit var gotologin : TextView

    lateinit var  firebaseAuth : FirebaseAuth
    lateinit var firebaseUser : FirebaseUser



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        supportActionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()

        emailETsignup = findViewById(R.id.signupemail)
        passwordETsignup = findViewById(R.id.signuppassword)
        loginBtn = findViewById(R.id.signup)
        gotologin = findViewById(R.id.gotologin)

        gotologin.setOnClickListener {
            val intent = Intent(this@Signup , MainActivity::class.java)
            startActivity(intent)
        }
        loginBtn.setOnClickListener {
            val email = emailETsignup.text.toString().trim()
            val password = passwordETsignup.text.toString().trim()
            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(this , "Please full up all information ", Toast.LENGTH_SHORT).show()
            }
            else if (password.length <7 ){
                Toast.makeText(this , "the Passsword Must be Greater thsn 7 digit ", Toast.LENGTH_SHORT).show()
            }
            else {
                //firebase registeration
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                    if (it.isSuccessful){
                    Toast.makeText(this,"create user successfuly", Toast.LENGTH_SHORT).show()
                    sendVerificationEmail()

                    } else {
                    Toast.makeText(this,"Something went wrong try again later", Toast.LENGTH_SHORT).show()
                }
                }
            }
        }
    }

    private fun sendVerificationEmail() {
        firebaseUser = firebaseAuth.currentUser!!
        if (firebaseUser!=null){
            firebaseUser.sendEmailVerification()

            Toast.makeText(this,"Verification Email is Sended ", Toast.LENGTH_SHORT).show()
            firebaseAuth.signOut()
            finish()
            intent = Intent(this@Signup , MainActivity::class.java)
            startActivity(intent)
        } else{
            Toast.makeText(this,"Falid to Verification Email , Try Again ", Toast.LENGTH_SHORT).show()

        }
    }

}