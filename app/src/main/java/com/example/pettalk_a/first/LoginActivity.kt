package com.example.pettalk_a.first

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.pettalk_a.MainActivity
import com.example.pettalk_a.R
import com.example.pettalk_a.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {

        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val login_btn = findViewById<Button>(R.id.goMainBtn)

        login_btn.setOnClickListener{

            val id = findViewById<EditText>(R.id.idArea).text.toString()
            val pwd = findViewById<EditText>(R.id.pwdArea).text.toString()

            auth.signInWithEmailAndPassword(id, pwd)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                    } else {
                        Toast.makeText(this, "NO", Toast.LENGTH_SHORT).show()
                    }
                }
        }


    }
}